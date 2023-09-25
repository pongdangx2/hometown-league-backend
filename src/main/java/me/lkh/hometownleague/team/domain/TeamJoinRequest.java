package me.lkh.hometownleague.team.domain;

public class TeamJoinRequest {
    private final Integer id;
    private final String userId;
    private final Integer teamId;
    private final String description;
    private final String processYn;

    public TeamJoinRequest(Integer id, String userId, Integer teamId, String description, String processYn) {
        this.id = id;
        this.userId = userId;
        this.teamId = teamId;
        this.description = description;
        this.processYn = processYn;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getProcessYn() {
        return processYn;
    }

    @Override
    public String toString() {
        return "TeamJoinRequest{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", teamId=" + teamId +
                ", description='" + description + '\'' +
                ", processYn='" + processYn + '\'' +
                '}';
    }
}
