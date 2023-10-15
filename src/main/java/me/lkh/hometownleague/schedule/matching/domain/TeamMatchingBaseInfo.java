package me.lkh.hometownleague.schedule.matching.domain;

public class TeamMatchingBaseInfo {
    private final Integer matchingRequestId;
    private final Integer teamId;
    private final Integer rankScore;

    public TeamMatchingBaseInfo(Integer matchingRequestId, Integer teamId, Integer rankScore) {
        this.matchingRequestId = matchingRequestId;
        this.teamId = teamId;
        this.rankScore = rankScore;
    }

    public Integer getMatchingRequestId() {
        return matchingRequestId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Integer getRankScore() {
        return rankScore;
    }
}
