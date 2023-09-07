package me.lkh.hometownleague.team.repository;

import me.lkh.hometownleague.team.domain.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamRepository {
    int insertTeam(Team team);

    int joinTeam(TeamUserMapping teamUserMapping);

    String selectIdByName(String name);

    int insertTeamPlayTime(TeamPlayTime teamPlayTime);

    int insertTeamPlayLocation(TeamPlayLocation teamPlayLocation);

    OwnerCheck selectOwnerCheck(Team team);

    int deleteTeamLogically(String teamId);
}
