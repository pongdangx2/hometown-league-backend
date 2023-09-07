package me.lkh.hometownleague.team.domain;

public class OwnerCheck {
    private final Integer teamId;
    private final String ownerYn;

    public OwnerCheck(Integer teamId, String ownerYn) {
        this.teamId = teamId;
        this.ownerYn = ownerYn;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getOwnerYn() {
        return ownerYn;
    }

    @Override
    public String toString() {
        return "OwnerCheck{" +
                "teamId=" + teamId +
                ", ownerYn='" + ownerYn + '\'' +
                '}';
    }
}
