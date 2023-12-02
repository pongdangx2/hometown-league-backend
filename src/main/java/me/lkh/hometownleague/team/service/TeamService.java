package me.lkh.hometownleague.team.service;

import me.lkh.hometownleague.common.code.RoleCode;
import me.lkh.hometownleague.common.exception.common.CommonErrorException;
import me.lkh.hometownleague.common.exception.team.*;
import me.lkh.hometownleague.common.util.HometownLeagueUtil;
import me.lkh.hometownleague.rank.service.RankService;
import me.lkh.hometownleague.team.domain.*;
import me.lkh.hometownleague.team.repository.TeamRepository;
import me.lkh.hometownleague.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TeamRepository teamRepository;

    private final RankService rankService;

    // 팀목록 조회 시 한페이지에 들어가는 개수는 일단 히스토리 조회 시와 같게 설정
    @Value("${matching.history-page-count}")
    private Integer teamPageCount;

    public TeamService(TeamRepository teamRepository, RankService rankService) {
        this.teamRepository = teamRepository;
        this.rankService = rankService;
    }

    /**
     * 팀생성
     *  1. 팀생성
     *  2. 소유주 등록
     *  3. 운동시간 생성
     *  4. 운동장소 생성
     * @param team
     * @param teamPlayTimes
     */
    @Transactional
    public Team makeTeam(Team team, List<TeamPlayTime> teamPlayTimes, List<TeamPlayLocation> teamPlayLocations){
        // 팀명이 이미 존재하는 경우
        if(isDuplicate(team.getName()))
            throw new DuplicateTeamNameException();

        // 1. 팀생성
        teamRepository.insertTeam(team);

        Optional<Team> optionalTeam = Optional.ofNullable(teamRepository.selectTeamByName(team.getName()));
        optionalTeam.ifPresentOrElse(selectedTeam -> {

                    // 2. 소유주 등록
                    joinTeam(team.getOwnerId(), selectedTeam.getId());

                    // 3. 운동시간 생성
                    insertPlayTime(selectedTeam.getId(), teamPlayTimes);

                    // 4. 운동지역 생성
                    insertPlayLocation(selectedTeam.getId(), teamPlayLocations);

                }
                ,()-> {
                    throw new CommonErrorException();
                });

        return optionalTeam.get();
    }

    /**
     * 팀 생성 시 운동 장소 데이터 Insert
     * @param teamId
     * @param teamPlayLocations
     */
    private void insertPlayLocation(Integer teamId, List<TeamPlayLocation> teamPlayLocations){
        teamPlayLocations.forEach(teamPlayLocation -> {
            try{
                TeamPlayLocation currentTeamPlayLocation = TeamPlayLocation.forInsertTeamPlayLocation(teamId, teamPlayLocation.getName(), teamPlayLocation.getLatitude(), teamPlayLocation.getLongitude(), teamPlayLocation.getLegalCode(), teamPlayLocation.getJibunAddress(), teamPlayLocation.getRoadAddress());
                if (1 != teamRepository.insertTeamPlayLocation(currentTeamPlayLocation)) {
                    throw new CannotInsertPlayLocationException("Cannot insert TeamPlayLocation info : " + currentTeamPlayLocation);
                }
            } catch(Exception e){
                throw new CannotInsertPlayLocationException();
            }
        });
    }

    /**
     * 팀 생성 시 운동 시간 데이터 Insert
     * @param teamId
     * @param teamPlayTimes
     */
    private void insertPlayTime(Integer teamId, List<TeamPlayTime> teamPlayTimes){
        teamPlayTimes.forEach(teamPlayTime -> {
            try {
                TeamPlayTime currentTeamPlayTime = TeamPlayTime.forInsertTeamPlayTime(teamId, teamPlayTime.getDayOfWeek(), teamPlayTime.getPlayTimeFrom(), teamPlayTime.getPlayTimeTo());
                if (1 != teamRepository.insertTeamPlayTime(currentTeamPlayTime)) {
                    throw new CannotInsertPlayTimeException("Cannot insert TeamPlayTime info : " + currentTeamPlayTime);
                }
            } catch (Exception e){
                throw new CannotInsertPlayTimeException();
            }
        });
    }

    /**
     * 소유주 등록
     * @param userId
     * @param teamId
     */
    private void joinTeam(String userId, Integer teamId){
        TeamUserMapping teamUserMapping = new TeamUserMapping(userId, teamId, RoleCode.OWNER.getRoleCode());
        teamRepository.joinTeam(teamUserMapping);
    }

    /**
     * 팀명 중복 여부 체크
     * @param name
     * @return
     */
    public boolean isDuplicate(String name){
        return Optional.ofNullable(teamRepository.selectTeamByName(name)).isPresent();
    }

    /**
     * 팀 삭제
     * @param userId 요청한 유저 아이디
     * @param teamId 삭제하고자 하는 팀 아이디
     */
    public void deleteTeam(String userId, Integer teamId){
        // 팀 존재 여부와 팀 소유주 여부 체크
        Team baseTeamInfo = isOwner(userId, teamId);

        // 소유주인 경우 논리삭제
        teamRepository.deleteTeamLogically(baseTeamInfo.getId());
    }

    /**
     * 팀이 존재하는지 확인, 소유주인지 확인
     * @param userId
     * @param teamId
     * @return
     */
    public Team isOwner(String userId, Integer teamId) {
        Team selectedTeam = isExist(teamId);
        if(!userId.equals(selectedTeam.getOwnerId())){
            // 요청한 사람이 팀 소유주가 아닐 경우
            throw new NotOwnerException();
        }

        return selectedTeam;
    }

    /**
     * 팀이 존재하는지 확인
     * @param teamId
     * @return
     */
    public Team isExist(Integer teamId){
        Team team = Team.forSelectTeam(teamId);
        Optional<Team> optionalTeam = Optional.ofNullable(teamRepository.selectTeam(team));

        // 팀이 존재하지 않는 경우
        optionalTeam.orElseThrow(NoSuchTeamIdException::new);
        return optionalTeam.get();
    }

    /**
     * 팀 정보 조회
     * @param teamId
     * @return
     */
    public Team selectTeam(Integer teamId) {
        Team baseTeamInfo = isExist(teamId);
        return Team.forSelectTeamResponse(baseTeamInfo.getId()
                                        , baseTeamInfo.getName()
                                        , baseTeamInfo.getCiPath()
                                        , baseTeamInfo.getDescription()
                                        , baseTeamInfo.getRankScore()
                                        , rankService.getRankName(baseTeamInfo.getRankScore())
                                        , baseTeamInfo.getKind()
//                                        , baseTeamInfo.getOwnerYn()
                                        , baseTeamInfo.getOwnerId()
                                        , teamRepository.selectTeamPlayTime(teamId)
                                        , teamRepository.selectTeamPlayLocation(teamId));
    }

    /**
     * 팀 정보 수정
     * @param team
     * @param userId
     */
    public void updateTeam(Team team, String userId){
        // 팀 존재 여부와 팀 소유주 여부 체크
        isOwner(userId, team.getId());

        teamRepository.updateTeam(team);
    }

    /**
     * 팀 운동 시간 업데이트
     * @param teamId
     * @param userId
     * @param teamPlayTimes
     */
    @Transactional
    public void updateTeamPlayTime(Integer teamId, String userId, List<TeamPlayTime> teamPlayTimes){
        // 팀 존재 여부와 팀 소유주 여부 체크
        isOwner(userId, teamId);

        teamPlayTimes.forEach(teamPlayTime -> {
            if(0 == teamRepository.updateTeamPlayTime(new TeamPlayTime(teamPlayTime.getId()
                                                    , teamId
                                                    , teamPlayTime.getDayOfWeek()
                                                    , teamPlayTime.getPlayTimeFrom()
                                                    , teamPlayTime.getPlayTimeTo()
            ))){
                throw new CannotUpdatePlayTimeException(teamPlayTime.toString());
            }
        });
    }

    /**
     * 팀 운동 장소 업데이트
     * @param teamId
     * @param userId
     * @param teamPlayLocations
     */
    @Transactional
    public void updateTeamPlayLocation(Integer teamId, String userId, List<TeamPlayLocation> teamPlayLocations){
        // 팀 존재 여부와 팀 소유주 여부 체크
        isOwner(userId, teamId);

        teamPlayLocations.forEach(teamPlayLocation -> {
            if(0 == teamRepository.updateTeamPlayLocation(new TeamPlayLocation(
                    teamPlayLocation.getId()
                    ,teamPlayLocation.getName()
                    ,teamId
                    ,teamPlayLocation.getLatitude()
                    ,teamPlayLocation.getLongitude()
                    ,teamPlayLocation.getJibunAddress()
                    ,teamPlayLocation.getRoadAddress()
                    ,teamPlayLocation.getLegalCode()
            ))){
                throw new CannotUpdatePlayLocationException(teamPlayLocation.toString());
            }
        });
    }

    /**
     * 팀 소속 선수 조회
     * @param teamId
     * @return
     */
    public List<User> selectUserOfTeam(Integer teamId){
        return teamRepository.selectUserOfTeam(teamId);
    }

    /**
     * 팀 소유주 변경
     * @param ownerId
     * @param teamId
     * @param userId
     */
    @Transactional
    public void updateTeamOwner(String ownerId, Integer teamId, String userId){
        // 팀 존재 여부와 팀 소유주 여부 체크
        isOwner(ownerId, teamId);

        if(0 == teamRepository.updatePlayerRole(userId, String.valueOf(teamId), RoleCode.OWNER.getRoleCode())){
            throw new NoSuchPlayerException("신규 Owner 역할 업데이트 실패");
        }

        if(0 == teamRepository.updatePlayerRole(ownerId, String.valueOf(teamId), RoleCode.PLAYER.getRoleCode())){
            throw new NoSuchPlayerException("기존 Owner 역할 업데이트 실패");
        }
    }

    /**
     *
     * @param addressSi
     * @param addressGungu
     * @param fromScore
     * @param toScore
     * @param dayOfWeek
     * @param time
     * @param name
     * @return
     */
    public List<Team> selectTeamList(String addressSi, String addressGungu, Integer fromScore, Integer toScore, Integer dayOfWeek, String time, String name, Integer page){
        return teamRepository.selectTeamList(addressSi
                , addressGungu
                , HometownLeagueUtil.integerToNullableString(fromScore)
                , HometownLeagueUtil.integerToNullableString(toScore)
                , HometownLeagueUtil.integerToNullableString(dayOfWeek)
                , time
                , name
                , String.valueOf(teamPageCount * (page-1))
                , String.valueOf(teamPageCount));
    }

    /**
     * 팀에 가입 요청
     * @param teamId
     * @param userId
     * @param description
     */
    public void joinRequest(String teamId, String userId, String description){
        Team team = Team.forSelectTeam(Integer.valueOf(teamId));

        // 1. 존재하는 팀인지 체크
        Optional.ofNullable(teamRepository.selectTeam(team)).orElseThrow(NoSuchTeamIdException::new);

        // 2. 이미 가입된 팀인지 체크
        Optional.ofNullable(teamRepository.selectJoinedTeam(userId, teamId)).ifPresent(joinedTeam -> {
            throw new AlreadyJoinedTeamException();
        });

        // 3. 이미 가입요청한 팀인지 체크
        Optional.ofNullable(teamRepository.selectJoinRequest(userId, teamId)).ifPresent(teamJoinRequest -> {
            logger.debug(teamJoinRequest.toString());

            throw new DuplicateTeamJoinRequestException();
        });

        // 4. 가입요청 생성
        if(0 == teamRepository.insertJoinRequest(userId, teamId, description)){
            throw new CannotRequestJoinTeamException();
        }
    }

    /**
     * 팀 가입요청 목록 조회
     * @param teamId
     * @return
     */
    public List<TeamJoinRequestUserProfile> selectJoinRequest(Integer teamId){
        // 소유주 여부 체크
//        isOwner(userId, teamId);
        isExist(teamId);

        return teamRepository.selectJoinRequestUser(teamId);
    }

    /**
     * 팀 가입요청 승인
     * @param userId
     * @param teamId
     * @param joinRequestId
     */
    @Transactional
    public void acceptJoinRequest(String userId, Integer teamId, Integer joinRequestId){
        // 팀 존재 여부와 팀 소유주 여부 체크
        isOwner(userId, teamId);

        // 가입
        if(0 == teamRepository.insertTeamUserMapping(joinRequestId))
            throw new CannotProduceTeamUserMappingException("가입 데이터 생성 실패");

        // 요청 삭제
        if(0 == teamRepository.deleteJoinRequest(joinRequestId))
            throw new CannotProduceTeamUserMappingException("요청 데이터 삭제 실패");
    }

    /**
     * 팀 탈퇴
     * @param teamId
     */
    public void leaveTeam(String userId, Integer teamId){
        isExist(teamId);
        teamRepository.leaveTeam(String.valueOf(teamId), userId);
    }
}
