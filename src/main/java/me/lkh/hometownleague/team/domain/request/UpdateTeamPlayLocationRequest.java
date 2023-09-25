package me.lkh.hometownleague.team.domain.request;

import me.lkh.hometownleague.team.domain.TeamPlayLocation;

import java.util.List;

public class UpdateTeamPlayLocationRequest {
    private final Integer teamId;
    private final List<TeamPlayLocation> location;

    public UpdateTeamPlayLocationRequest(Integer teamId, List<TeamPlayLocation> location) {
        this.teamId = teamId;
        this.location = location;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public List<TeamPlayLocation> getLocation() {
        return location;
    }
}
