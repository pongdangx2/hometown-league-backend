package me.lkh.hometownleague.team.domain.request;

public class MakeJoinAcceptRequest {
    private final Integer teamId;
    private final Integer joinRequestId;

    public MakeJoinAcceptRequest(Integer teamId, Integer joinRequestId) {
        this.teamId = teamId;
        this.joinRequestId = joinRequestId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Integer getJoinRequestId() {
        return joinRequestId;
    }
}
