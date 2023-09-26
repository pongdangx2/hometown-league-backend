package me.lkh.hometownleague.matching.domain;

public class MatchingListElement {
    private final Integer teamId;
    private final Integer matchingRequestId;
    private final String name;
    private final Integer rankScore;
    private final Integer kind;
    private final String description;
    private final String status;

    public MatchingListElement(Integer teamId, Integer matchingRequestId, String name, Integer rankScore, Integer kind, String description, String status) {
        this.teamId = teamId;
        this.matchingRequestId = matchingRequestId;
        this.name = name;
        this.rankScore = rankScore;
        this.kind = kind;
        this.description = description;
        this.status = status;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Integer getMatchingRequestId() {
        return matchingRequestId;
    }

    public String getName() {
        return name;
    }

    public Integer getRankScore() {
        return rankScore;
    }

    public Integer getKind() {
        return kind;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
