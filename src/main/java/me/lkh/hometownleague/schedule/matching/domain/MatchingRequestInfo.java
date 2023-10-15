package me.lkh.hometownleague.schedule.matching.domain;

public class MatchingRequestInfo {
    private final Integer id;
    private final Integer teamId;
    private final String processYn;
    private final Integer rankScore;

    public MatchingRequestInfo(Integer id, Integer teamId, String processYn, Integer rankScore) {
        this.id = id;
        this.teamId = teamId;
        this.processYn = processYn;
        this.rankScore = rankScore;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getProcessYn() {
        return processYn;
    }

    public Integer getRankScore() {
        return rankScore;
    }

}
