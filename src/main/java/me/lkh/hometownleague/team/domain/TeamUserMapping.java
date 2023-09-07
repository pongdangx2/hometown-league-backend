package me.lkh.hometownleague.team.domain;

public class TeamUserMapping {

    private final String userId;

    private final String teamId;

    private final String roleCode;

    public TeamUserMapping(String userId, String teamId, String roleCode) {
        this.userId = userId;
        this.teamId = teamId;
        this.roleCode = roleCode;
    }

    public String getUserId() {
        return userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getRoleCode() {
        return roleCode;
    }
}
