package me.lkh.hometownleague.team.service;

import me.lkh.hometownleague.common.code.RoleCode;
import me.lkh.hometownleague.common.exception.common.CommonErrorException;
import me.lkh.hometownleague.common.exception.team.CannotInsertPlayLocationException;
import me.lkh.hometownleague.common.exception.team.CannotInsertPlayTimeException;
import me.lkh.hometownleague.common.exception.team.DuplicateTeamNameException;
import me.lkh.hometownleague.team.repository.TeamRepository;
import me.lkh.hometownleague.team.service.domain.Team;
import me.lkh.hometownleague.team.service.domain.TeamPlayLocation;
import me.lkh.hometownleague.team.service.domain.TeamPlayTime;
import me.lkh.hometownleague.team.service.domain.TeamUserMapping;
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

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
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

    private void insertPlayLocation(String teamId, List<TeamPlayLocation> teamPlayLocations){
        teamPlayLocations.forEach(teamPlayLocation -> {
            try{
                TeamPlayLocation currentTeamPlayLocation = new TeamPlayLocation(teamId, teamPlayLocation.getLatitude(), teamPlayLocation.getLongitude(), teamPlayLocation.getLegalCode(), teamPlayLocation.getJibunAddress(), teamPlayLocation.getRoadAddress());
                if (1 != teamRepository.insertTeamPlayLocation(currentTeamPlayLocation)) {
                    throw new CannotInsertPlayLocationException("Cannot insert TeamPlayLocation info : " + currentTeamPlayLocation);
                }
            } catch(Exception e){
                throw new CannotInsertPlayLocationException();
            }
        });
    }

    private void insertPlayTime(String teamId, List<TeamPlayTime> teamPlayTimes){
        teamPlayTimes.forEach(teamPlayTime -> {
            try {
                TeamPlayTime currentTeamPlayTime = new TeamPlayTime(teamId, teamPlayTime.getDayOfWeek(), teamPlayTime.getPlayTimeFrom(), teamPlayTime.getPlayTimeTo());
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
    private void joinTeam(String userId, String teamId){
        TeamUserMapping teamUserMapping = new TeamUserMapping(userId, teamId, RoleCode.OWNER.getRoleCode());
        teamRepository.joinTeam(teamUserMapping);
    }

    /**
     * 팀명 중복 여부 체크
     * @param name
     * @return
     */
    public boolean isDuplicate(String name){
        String id = teamRepository.selectIdByName(name);
        return id != null;
    }
}
