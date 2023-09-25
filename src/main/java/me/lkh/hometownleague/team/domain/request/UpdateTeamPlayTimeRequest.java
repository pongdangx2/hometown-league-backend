package me.lkh.hometownleague.team.domain.request;

import me.lkh.hometownleague.team.domain.TeamPlayTime;

import java.util.List;

public class UpdateTeamPlayTimeRequest {
    private final Integer teamId;
    private final List<TeamPlayTime> time;

    public UpdateTeamPlayTimeRequest(Integer teamId, List<TeamPlayTime> time) {
        this.teamId = teamId;
        this.time = time;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public List<TeamPlayTime> getTime() {
        return time;
    }
}
