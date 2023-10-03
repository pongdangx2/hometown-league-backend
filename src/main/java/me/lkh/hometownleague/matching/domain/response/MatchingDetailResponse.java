package me.lkh.hometownleague.matching.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchingDetailResponse {
    private final MatchingDetailBase matchingDetail;
    private final MatchingDetailTeam ourTeam;
    private final MatchingDetailTeam otherTeam;

    public MatchingDetailResponse(MatchingDetailBase matchingDetail, MatchingDetailTeam ourTeam, MatchingDetailTeam otherTeam) {
        this.matchingDetail = matchingDetail;
        this.ourTeam = ourTeam;
        this.otherTeam = otherTeam;
    }

    public MatchingDetailBase getMatchingDetail() {
        return matchingDetail;
    }

    public MatchingDetailTeam getOurTeam() {
        return ourTeam;
    }

    public MatchingDetailTeam getOtherTeam() {
        return otherTeam;
    }
}
