package me.lkh.hometownleague.matching.domain;

/**
 * 매칭 결과 등록을 위한 요청 body
 */
public class MatchingResultReportRequest {
    private final Integer matchingRequestId;
    private final Integer ourTeamScore;
    private final Integer otherTeamScore;

    public MatchingResultReportRequest(Integer matchingRequestId, Integer ourTeamScore, Integer otherTeamScore) {
        this.matchingRequestId = matchingRequestId;
        this.ourTeamScore = ourTeamScore;
        this.otherTeamScore = otherTeamScore;
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
