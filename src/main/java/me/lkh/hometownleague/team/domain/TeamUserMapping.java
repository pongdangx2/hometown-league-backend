package me.lkh.hometownleague.team.domain;

public class TeamUserMapping {

    private final String userId;

    private final Integer teamId;

    private final String roleCode;

    public TeamUserMapping(String userId, Integer teamId, String roleCode) {
        this.userId = userId;
        this.teamId = teamId;
        this.roleCode = roleCode;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getRoleCode() {
        return roleCode;
    }
}
