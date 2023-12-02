package me.lkh.hometownleague.rank.domain;

public class MemberCount {
    private final Integer teamId;
    private final Integer count;

    public MemberCount(Integer teamId, Integer count) {
        this.teamId = teamId;
        this.count = count;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Integer getCount() {
        return count;
    }
}
