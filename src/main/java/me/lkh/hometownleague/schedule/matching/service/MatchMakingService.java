package me.lkh.hometownleague.schedule.matching.service;

import me.lkh.hometownleague.common.util.HometownLeagueUtil;
import me.lkh.hometownleague.matching.domain.MatchingQueueElement;
import me.lkh.hometownleague.matching.service.MatchingRedisService;
import me.lkh.hometownleague.schedule.matching.domain.*;
import me.lkh.hometownleague.schedule.matching.repository.MatchMakingRepository;
import me.lkh.hometownleague.schedule.matching.service.location.LocationFilteringStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class MatchMakingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MatchingRedisService matchingRedisService;

    private final MatchMakingRepository matchMakingRepository;

    @Qualifier("coordinateFilter")
    private final LocationFilteringStrategy locationFilteringStrategy;

    // 매칭 탐색 최대 시도 횟수
    @Value("${matching.max-number}")
    private int maxNumber;

    // 매칭 탐색 시 최대 거리 기준
    @Value("${matching.max-distance}")
    private int maxDistance;

    public MatchMakingService(MatchingRedisService matchingRedisService, MatchMakingRepository matchMakingRepository, LocationFilteringStrategy locationFilteringStrategy) {
        this.matchingRedisService = matchingRedisService;
        this.matchMakingRepository = matchMakingRepository;
        this.locationFilteringStrategy = locationFilteringStrategy;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void matchMakingJob(){
        // redis 대기열에서 가져오기
        MatchingQueueElement matchingQueueElement = matchingRedisService.popLeft();
//        MatchingQueueElement matchingQueueElement = matchingRedisService.getLeft();

        // 처리되지 않은 요청이 있는 경우에만 매칭 처리
        Optional.ofNullable(matchMakingRepository.selectMatchingRequestInfo(matchingQueueElement.getMatchingRequestId()))
                .ifPresent(matchingRequestInfo -> {
            try{
                // 1. 처리 되었다고 업데이트하여 다른 배치에서 중복작업을 방지
                matchMakingRepository.updateProcessYn(matchingRequestInfo.getId());

                // 최대 횟수까지만 시도
                int times = 1;
                while (times <= maxNumber){

                    // 2. 매칭이 성공하면 종료
                    if(matchingStep(matchingRequestInfo, times)){
                        return ;
                    }

                }

                // 3. 최대횟수 안에 매칭이 실패하면 최우선순위로 다시 넣고 종료
                throw new RuntimeException();

            } catch (RuntimeException runtimeException){
                runtimeException.printStackTrace();
                // 1. 처리중 오류 발생 시 대기열에 최우선순위로 다시 삽입 (Left)
                matchingRedisService.pushLeft(matchingQueueElement);

                // 2. RuntimeException을 발생시켜 transaction rollback
                throw new RuntimeException();
            }
        });
    }

    /**
     * 매칭되는 팀을 탐색
     * @param matchingRequestInfo
     * @param times
     * @return 찾았으면 true, 못찾았으면 false
     */
    private boolean matchingStep(MatchingRequestInfo matchingRequestInfo, int times){

        int scoreDiff = times * 10;
        // 1. 1차 필터링(점수 기준) - 내 점수와의 차이가 scoreDiff보다 작은 팀 목록 조회
        //    팀 ID가 우리팀 ID와 같은경우는 여기서 걸러짐
        List<TeamMatchingBaseInfo> teamListAfterScoreFilter = matchMakingRepository.selectTeamMatchingBaseInfo(matchingRequestInfo.getId(), matchingRequestInfo.getRankScore(), scoreDiff);
        if(teamListAfterScoreFilter.isEmpty())
            return false;

        // 2. 2차 필터링 (거리 기준)
        List<TeamMatchingBaseInfo> teamListAfterLocationFilter = locationFiltering(matchingRequestInfo.getTeamId(), teamListAfterScoreFilter);
        if(teamListAfterLocationFilter.isEmpty())
            return false;

        // 3. 3차 필터링 (시간 기준)
        Optional<TeamMatchingBaseInfo> teamAfterTimeFilter = timeFiltering(matchingRequestInfo.getTeamId(), teamListAfterLocationFilter);
        if(teamAfterTimeFilter.isEmpty())
            return false;

        // 4. 매칭 정보 DB 반영
        TeamMatchingBaseInfo matchedTeam = teamAfterTimeFilter.get();

        // 4-1. 이미 처리되어 업데이트하지 못한 경우 필터링
        if(matchMakingRepository.updateRequestInfo(matchedTeam.getMatchingRequestId()) != 1){
            return false;
        }

        // 4-2. 우리 팀의 매칭 시간은 현재로 업데이트
        matchMakingRepository.updateProcessTimestamp(matchingRequestInfo.getId());

        // 4-3. 구체적 매칭정보를 다시 확정하여 업데이트
        matchMaking(matchingRequestInfo, matchedTeam);

        return true;
    }

    // 2. 2차 필터링 - 위치 기반 필터링
    private List<TeamMatchingBaseInfo> locationFiltering(Integer teamId, List<TeamMatchingBaseInfo> scoreFilterdTeamList){
        // 1. 우리팀 경기 장소 조회
        List<TeamMatchingLocation> myTeamMatchingLocation = matchMakingRepository.selectMyTeamMatchingLocation(teamId);
        // 2. 경기장소로 2차 필터링
        return locationFilteringStrategy.doFilter(myTeamMatchingLocation, scoreFilterdTeamList);
    }

    // 3. 3차 필터링 - 경기 시간 기반 필터링
    private Optional<TeamMatchingBaseInfo> timeFiltering(Integer teamId, List<TeamMatchingBaseInfo> teamListAfterLocationFilter) {

        // 1. 우리팀 경기 시간 조회
        List<TeamMatchingTime> myTeamMatchingTimeList = matchMakingRepository.selectTeamMatchingTime(teamId);

        // 2. 대상 팀들의 경기 시간 조회
        List<TeamMatchingTime> teamMatchingTimeList = matchMakingRepository.selectPlayTimeList(teamListAfterLocationFilter);
        for(TeamMatchingTime otherMatchingTime : teamMatchingTimeList) {
            for(TeamMatchingTime myMatchingTime : myTeamMatchingTimeList) {
                if (isPossibleTime(myMatchingTime, otherMatchingTime)){
                    // 찾은 경우
                    return teamListAfterLocationFilter.stream()
                            .filter(teamBaseInfo -> teamBaseInfo.getTeamId() == otherMatchingTime.getTeamId())
                            .findFirst();
                }
            }
        }

        // 찾지 못한 경우
        return Optional.empty();
    }

    // 3-1. 같은요일이고 경기시간이 조금이라도 겹치는지 여부를 리턴하는 메서드
    private boolean isPossibleTime(TeamMatchingTime myMatchingTime, TeamMatchingTime otherMatchingTime){
        // 1. 요일이 다르면 바로 false
        if(myMatchingTime.getDayOfWeek() != otherMatchingTime.getDayOfWeek())
            return false;

        // 우리 팀의 운동 가능 시간 From, To
        LocalTime myFromTime = HometownLeagueUtil.getLocalTimeFromString(myMatchingTime.getFromTime());
        LocalTime myToTime = HometownLeagueUtil.getLocalTimeFromString(myMatchingTime.getToTime());

        // 다른 팀의 운동 가능 시간 From, To
        LocalTime otherFromTime = HometownLeagueUtil.getLocalTimeFromString(otherMatchingTime.getFromTime());
        LocalTime otherToTime = HometownLeagueUtil.getLocalTimeFromString(otherMatchingTime.getToTime());

        // 우리팀의 from Time >>> 다른팀의 from time >>> 우리팀의 To Time 인 경우
        // = 경기 가능
        if(myFromTime.compareTo(otherFromTime) <= 0 && otherFromTime.compareTo(myToTime) < 0){
            return true;
        }

        // 우리팀의 from Time >>> 다른팀의 To Time >>> 우리팀의 To Time 인 경우
        // = 경기 가능
        if(myFromTime.compareTo(otherToTime) < 0 && otherToTime.compareTo(myToTime) <= 0){
            return true;
        }

        return false;
    }

    // 4. 매칭 정보 생성
    public void matchMaking(MatchingRequestInfo ourMatchingRequestInfo, TeamMatchingBaseInfo otherMatchingBaseInfo){

        // 1. 경기장소 조회
        List<TeamMatchingLocation> myTeamMatchingLocation = matchMakingRepository.selectMyTeamMatchingLocation(ourMatchingRequestInfo.getTeamId());
        List<TeamMatchingLocation> otherTeamMatchingLocation = matchMakingRepository.selectMyTeamMatchingLocation(otherMatchingBaseInfo.getTeamId());

        TeamMatchingLocation matchLocation = locationFilteringStrategy.getMatchingLocation(myTeamMatchingLocation, otherTeamMatchingLocation);

        // 2. 경기 시간 조회
        List<TeamMatchingTime> myTeamMatchingTimeList = matchMakingRepository.selectTeamMatchingTime(ourMatchingRequestInfo.getTeamId());
        List<TeamMatchingTime> otherTeamMatchingTimeList = matchMakingRepository.selectTeamMatchingTime(otherMatchingBaseInfo.getTeamId());
        TeamMatchingTime matchTime = getMatchingTime(myTeamMatchingTimeList, otherTeamMatchingTimeList);

        // 3. 매치 매핑 데이터 INSERT
        TeamMatchingRequestMapping teamMatchingRequestMapping = new TeamMatchingRequestMapping(ourMatchingRequestInfo.getId()
                , otherMatchingBaseInfo.getMatchingRequestId()
                , HometownLeagueUtil.getMatchTimestamp(matchTime)
                , matchLocation.getRoadAddress()
                , matchLocation.getJibunAddress()
                , matchLocation.getLatitude()
                , matchLocation.getLongitude());
        if(matchMakingRepository.insertTeamMatchingRequestMapping(teamMatchingRequestMapping) != 1){
            logger.error("ERROR: matching_request_mapping INSERT FAILED:"+ ourMatchingRequestInfo.toString());
            throw new RuntimeException();
        }

        // 4. 우리팀의 match_info INSERT
        if(matchMakingRepository.insertMatchingInfo(ourMatchingRequestInfo.getId()) != 1){
            logger.error("ERROR: matching_info INSERT FAILED:"+ ourMatchingRequestInfo.toString());
            throw new RuntimeException();
        }

        // 5. 상대팀의 match_info INSERT
        if(matchMakingRepository.insertMatchingInfo(otherMatchingBaseInfo.getMatchingRequestId()) != 1){
            logger.error("ERROR: matching_info INSERT FAILED:"+ otherMatchingBaseInfo.toString());
            throw new RuntimeException();
        }
    }

    private TeamMatchingTime getMatchingTime(List<TeamMatchingTime> myTeamMatchingTimeList, List<TeamMatchingTime> otherTeamMatchingTimeList){
        for(TeamMatchingTime myTeamMatchingTime : myTeamMatchingTimeList){
            for(TeamMatchingTime otherTeamMatchingTime : otherTeamMatchingTimeList){
                if(isPossibleTime(myTeamMatchingTime, otherTeamMatchingTime))
                    return myTeamMatchingTime;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "MatchMakingService{" +
                "matchingRedisService=" + matchingRedisService +
                ", matchMakingRepository=" + matchMakingRepository +
                ", locationFilteringStrategy=" + locationFilteringStrategy +
                ", maxNumber=" + maxNumber +
                ", maxDistance=" + maxDistance +
                '}';
    }
}
