package me.lkh.hometownleague.matching.domain;

public class MatchingRequestMappingScoreUpdate {
    private final String status;
    private final Integer aTeamRequestId;
    private final Integer bTeamRequestId;
    private final Integer aTeamScore;
    private final Integer bTeamScore;

    public MatchingRequestMappingScoreUpdate(String status, Integer aTeamRequestId, Integer bTeamRequestId, Integer aTeamScore, Integer bTeamScore) {
        this.status = status;
        this.aTeamRequestId = aTeamRequestId;
        this.bTeamRequestId = bTeamRequestId;
        this.aTeamScore = aTeamScore;
        this.bTeamScore = bTeamScore;
    }

    public String getStatus() {
        return status;
    }

    public Integer getaTeamRequestId() {
        return aTeamRequestId;
    }

    public Integer getbTeamRequestId() {
        return bTeamRequestId;
    }

    public Integer getaTeamScore() {
        return aTeamScore;
    }

    public Integer getbTeamScore() {
        return bTeamScore;
    }
}
