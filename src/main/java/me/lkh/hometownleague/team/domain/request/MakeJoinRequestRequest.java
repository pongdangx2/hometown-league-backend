package me.lkh.hometownleague.team.domain.request;

public class MakeJoinRequestRequest {

    private final Integer teamId;
    private final String description;

    public MakeJoinRequestRequest(Integer teamId, String description) {
        this.teamId = teamId;
        this.description = description;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getDescription() {
        return description;
    }
}
