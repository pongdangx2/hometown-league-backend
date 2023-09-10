package me.lkh.hometownleague.team.repository;

import me.lkh.hometownleague.team.domain.*;
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
}
