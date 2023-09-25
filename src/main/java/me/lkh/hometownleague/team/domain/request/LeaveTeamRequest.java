package me.lkh.hometownleague.team.domain.request;

public class LeaveTeamRequest {
    private final String userId;
    private final Integer teamId;

    public LeaveTeamRequest(String userId, Integer teamId) {
        this.userId = userId;
        this.teamId = teamId;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    @Override
    public String toString() {
        return "LeaveTeamRequest{" +
                "userId='" + userId + '\'' +
                ", teamId=" + teamId +
                '}';
    }
}
