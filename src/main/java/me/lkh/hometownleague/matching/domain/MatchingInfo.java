package me.lkh.hometownleague.matching.domain;

public class MatchingInfo {
    private final Integer id;
    private final Integer matchingRequestId;
    private final String status;
    private final String accept_timestamp;

    public MatchingInfo(Integer id, Integer matchingRequestId, String status, String accept_timestamp) {
        this.id = id;
        this.matchingRequestId = matchingRequestId;
        this.status = status;
        this.accept_timestamp = accept_timestamp;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMatchingRequestId() {
        return matchingRequestId;
    }

    public String getStatus() {
        return status;
    }

    public String getAccept_timestamp() {
        return accept_timestamp;
    }
}
