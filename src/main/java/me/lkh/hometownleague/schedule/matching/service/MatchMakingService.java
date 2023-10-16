package me.lkh.hometownleague.schedule.matching.service;

import me.lkh.hometownleague.matching.domain.MatchingQueueElement;
import me.lkh.hometownleague.matching.service.MatchingRedisService;
import me.lkh.hometownleague.schedule.matching.domain.MatchingRequestInfo;
import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingBaseInfo;
import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingLocation;
import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingTime;
import me.lkh.hometownleague.schedule.matching.repository.MatchMakingRepository;
import me.lkh.hometownleague.schedule.matching.service.location.LocationFilteringStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
//        MatchingQueueElement matchingQueueElement = matchingRedisService.popLeft();
        MatchingQueueElement matchingQueueElement = matchingRedisService.getLeft();
        Optional<MatchingRequestInfo> optionalMatchingRequestInfo = Optional.ofNullable(matchMakingRepository.selectMatchingRequestInfo(matchingQueueElement.getMatchingRequestId()));
        optionalMatchingRequestInfo.ifPresent(matchingRequestInfo -> {
            // 이미 처리되지 않은 경우에만 매칭 처리
            if(!"Y".equals(matchingRequestInfo.getProcessYn())){
                try{
                    // 1. 처리 되었다고 업데이트하여 다른 배치에서 중복작업을 방지
                    matchMakingRepository.updateProcessYn(matchingRequestInfo.getId());

                    // 최대 횟수까지만 시도
                    int times = 1;
                    while (times <= maxNumber){

                        // 2. 매칭이 성공하면 종료
                        if(matchingStep(matchingRequestInfo, times)){
                            // 매칭 시간은 현재로 업데이트
                            matchMakingRepository.updateProcessTimestamp(matchingRequestInfo.getId());
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
            }
        });
    }

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

        return true;
    }

    // 경기 장소 기반 필터링
    private Optional<TeamMatchingBaseInfo> timeFiltering(Integer teamId, List<TeamMatchingBaseInfo> teamListAfterLocationFilter) {

        Optional<TeamMatchingBaseInfo> result = Optional.empty();

        // 1. 우리팀 경기 시간 조회
        List<TeamMatchingTime> myTeamMatchingTime = matchMakingRepository.selectMyTeamMatchingTime(teamId);

        // 2. 대상 팀들의 경기 시간 조회
        List<TeamMatchingTime> teamMatchingTimeList = matchMakingRepository.selectPlayTimeList(teamListAfterLocationFilter);
        for(TeamMatchingTime teamMatchingTime : teamMatchingTimeList) {
            //@TODO : 여기서 필터링하면됨.
        }

        return result;
    }

    // 위치 기반 필터링
    private List<TeamMatchingBaseInfo> locationFiltering(Integer teamId, List<TeamMatchingBaseInfo> scoreFilterdTeamList){
        // 1. 우리팀 경기 장소 조회
        List<TeamMatchingLocation> myTeamMatchingLocation = matchMakingRepository.selectMyTeamMatchingLocation(teamId);
        // 2. 경기장소로 2차 필터링
        return locationFilteringStrategy.doFilter(myTeamMatchingLocation, scoreFilterdTeamList);
    }

}
