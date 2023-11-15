package me.lkh.hometownleague.matching.domain;

public class MatchingResultInfo {
    private final Integer id;
    private final Integer matchingRequestId;
    private final Integer ourTeamScore;
    private final Integer otherTeamScore;

    public MatchingResultInfo(Integer id, Integer matchingRequestId, Integer ourTeamScore, Integer otherTeamScore) {
        this.id = id;
        this.matchingRequestId = matchingRequestId;
        this.ourTeamScore = ourTeamScore;
        this.otherTeamScore = otherTeamScore;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMatchingRequestId() {
        return matchingRequestId;
    }

    public Integer getOurTeamScore() {
        return ourTeamScore;
    }

    public Integer getOtherTeamScore() {
        return otherTeamScore;
    }
}
