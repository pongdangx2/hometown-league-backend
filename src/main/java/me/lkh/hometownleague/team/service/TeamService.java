package me.lkh.hometownleague.team.service;

import me.lkh.hometownleague.common.code.RoleCode;
import me.lkh.hometownleague.common.exception.common.CommonErrorException;
import me.lkh.hometownleague.common.exception.team.*;
import me.lkh.hometownleague.common.util.RankService;
import me.lkh.hometownleague.team.repository.TeamRepository;
import me.lkh.hometownleague.team.domain.Team;
import me.lkh.hometownleague.team.domain.TeamPlayLocation;
import me.lkh.hometownleague.team.domain.TeamPlayTime;
import me.lkh.hometownleague.team.domain.TeamUserMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TeamRepository teamRepository;

    private final RankService rankService;

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
    public void makeTeam(Team team, List<TeamPlayTime> teamPlayTimes, List<TeamPlayLocation> teamPlayLocations){
        // 팀명이 이미 존재하는 경우
        if(isDuplicate(team.getName()))
            throw new DuplicateTeamNameException();

        // 1. 팀생성
        teamRepository.insertTeam(team);

        Optional.ofNullable(teamRepository.selectIdByName(team.getName())).ifPresentOrElse(teamId -> {

                    // 2. 소유주 등록
                    joinTeam(team.getOwnerId(), teamId);

                    // 3. 운동시간 생성
                    insertPlayTime(teamId, teamPlayTimes);

                    // 4. 운동지역 생성
                    insertPlayLocation(teamId, teamPlayLocations);
                }
                ,()-> {
                    throw new CommonErrorException();
                });
    }

    private void insertPlayLocation(Integer teamId, List<TeamPlayLocation> teamPlayLocations){
        teamPlayLocations.forEach(teamPlayLocation -> {
            try{
                TeamPlayLocation currentTeamPlayLocation = TeamPlayLocation.forInsertTeamPlayLocation(teamId, teamPlayLocation.getLatitude(), teamPlayLocation.getLongitude(), teamPlayLocation.getLegalCode(), teamPlayLocation.getJibunAddress(), teamPlayLocation.getRoadAddress());
                if (1 != teamRepository.insertTeamPlayLocation(currentTeamPlayLocation)) {
                    throw new CannotInsertPlayLocationException("Cannot insert TeamPlayLocation info : " + currentTeamPlayLocation);
                }
            } catch(Exception e){
                throw new CannotInsertPlayLocationException();
            }
        });
    }

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
        Integer id = teamRepository.selectIdByName(name);
        return id != null;
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

    private Team isOwner(String userId, Integer teamId) {
        Team team = Team.forOwnerCheck(teamId, userId);
        Optional<Team> optionalTeam = Optional.ofNullable(teamRepository.selectTeam(team));

        optionalTeam
                .ifPresentOrElse(baseTeamInfo -> {
                    if(!"Y".equals(baseTeamInfo.getOwnerYn())){
                        // 요청한 사람이 팀 소유주가 아닐 경우
                        throw new NotOwnerException();
                    }
                },() -> {
                    // 팀이 존재하지 않는 경우
                    throw new NoSuchTeamIdException();
                });

        return optionalTeam.get();
    }

    public Team selectTeam(String userId, Integer teamId) {
        Optional<Team> optionalBaseTeamInfo = Optional.ofNullable(teamRepository.selectTeam(Team.forOwnerCheck(teamId, userId)));
        // 팀이 존재하지 않는 경우
        optionalBaseTeamInfo.orElseThrow(NoSuchTeamIdException::new);
        Team baseTeamInfo = optionalBaseTeamInfo.get();

        return Team.forSelectTeamResponse(baseTeamInfo.getId()
                                        , baseTeamInfo.getName()
                                        , baseTeamInfo.getCiPath()
                                        , baseTeamInfo.getDescription()
                                        , baseTeamInfo.getRankScore()
                                        , rankService.getRankName(baseTeamInfo.getRankScore())
                                        , baseTeamInfo.getKind()
                                        , baseTeamInfo.getOwnerYn()
                                        , teamRepository.selectTeamPlayTime(teamId)
                                        , teamRepository.selectTeamPlayLocation(teamId));
    }

    public void updateTeam(Team team, String userId){
        // 팀 존재 여부와 팀 소유주 여부 체크
        isOwner(userId, team.getId());

        teamRepository.updateTeam(team);
    }
}
