package me.lkh.hometownleague.team.repository;

import me.lkh.hometownleague.team.service.domain.Team;
import me.lkh.hometownleague.team.service.domain.TeamPlayLocation;
import me.lkh.hometownleague.team.service.domain.TeamPlayTime;
import me.lkh.hometownleague.team.service.domain.TeamUserMapping;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamRepository {
    int insertTeam(Team team);

    int joinTeam(TeamUserMapping teamUserMapping);

    String selectIdByName(String name);

    int insertTeamPlayTime(TeamPlayTime teamPlayTime);

    int insertTeamPlayLocation(TeamPlayLocation teamPlayLocation);
}
