package me.lkh.hometownleague.schedule.matching.domain;

public class MatchingRequestDeleteCheck {
    private final Integer matchingRequestId;
    private final Integer teamId;
    private final String processYn;
    private final String status;

    public MatchingRequestDeleteCheck(Integer matchingRequestId, Integer teamId, String processYn, String status) {
        this.matchingRequestId = matchingRequestId;
        this.teamId = teamId;
        this.processYn = processYn;
        this.status = status;
    }

    public Integer getMatchingRequestId() {
        return matchingRequestId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getProcessYn() {
        return processYn;
    }

    public String getStatus() {
        return status;
    }
}
