package me.lkh.hometownleague.team.repository;

import me.lkh.hometownleague.team.domain.*;
import me.lkh.hometownleague.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRepository {
    int insertTeam(Team team);

    int joinTeam(TeamUserMapping teamUserMapping);

    Integer selectIdByName(String name);

    int insertTeamPlayTime(TeamPlayTime teamPlayTime);

    int insertTeamPlayLocation(TeamPlayLocation teamPlayLocation);

    int deleteTeamLogically(Integer teamId);

    Team selectTeam(Team team);

    List<TeamPlayLocation> selectTeamPlayLocation(Integer teamId);

    List<TeamPlayTime> selectTeamPlayTime(Integer teamId);

    int updateTeam(Team team);

    int updateTeamPlayTime(TeamPlayTime teamPlayTime);

    int updateTeamPlayLocation(TeamPlayLocation teamPlayLocation);

    List<User> selectUserOfTeam(Integer teamId);

    int updatePlayerRole(String userId, String teamId, String role);

    List<Team> selectTeamList(String legalCode, String fromScore, String toScore, String dayOfWeek, String time, String name);

    Integer selectJoinedTeam(String userId, String teamId);

    TeamJoinRequest selectJoinRequest(String userId, String teamId);

    int insertJoinRequest(String userId, String teamId, String description);

    List<TeamJoinRequestUserProfile> selectJoinRequestUser(Integer teamId);

}
