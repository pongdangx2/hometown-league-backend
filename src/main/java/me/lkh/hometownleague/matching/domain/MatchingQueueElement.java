package me.lkh.hometownleague.matching.domain;

import me.lkh.hometownleague.common.util.HometownLeagueUtil;

public class MatchingQueueElement {
    private final Integer matchingRequestId;
    private final Integer teamId;
    private final String requestTimestamp;

    public static MatchingQueueElement makeMatchingQueueElementOfNow(Integer matchingRequestId, Integer teamId){
        return new MatchingQueueElement(matchingRequestId, teamId, HometownLeagueUtil.getCurrentTimestamp());
    }

    private MatchingQueueElement(Integer matchingRequestId, Integer teamId, String requestTimestamp) {
        this.matchingRequestId = matchingRequestId;
        this.teamId = teamId;
        this.requestTimestamp = requestTimestamp;
    }

    public Integer getMatchingRequestId() {
        return matchingRequestId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getRequestTimestamp() {
        return requestTimestamp;
    }
}
