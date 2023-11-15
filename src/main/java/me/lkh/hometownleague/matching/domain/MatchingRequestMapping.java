package me.lkh.hometownleague.matching.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchingRequestMapping {
    private final Integer id;
    private final Integer aTeamMatchingRequestId;
    private final Integer bTeamMatchingRequestId;
    private final String match_timestamp;
    private final String roadAddress;
    private final String jibunAddress;
    private final double latitude;
    private final double longitude;
    private final String status;
    private final Integer aTeamScore;
    private final Integer bTeamScore;
    private final String createTimestamp;

    public MatchingRequestMapping(Integer id, Integer aTeamMatchingRequestId, Integer bTeamMatchingRequestId, String match_timestamp, String roadAddress, String jibunAddress, double latitude, double longitude, String status, Integer aTeamScore, Integer bTeamScore, String createTimestamp) {
        this.id = id;
        this.aTeamMatchingRequestId = aTeamMatchingRequestId;
        this.bTeamMatchingRequestId = bTeamMatchingRequestId;
        this.match_timestamp = match_timestamp;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.aTeamScore = aTeamScore;
        this.bTeamScore = bTeamScore;
        this.createTimestamp = createTimestamp;
    }

    public Integer getId() {
        return id;
    }

    public Integer getaTeamMatchingRequestId() {
        return aTeamMatchingRequestId;
    }

    public Integer getbTeamMatchingRequestId() {
        return bTeamMatchingRequestId;
    }

    public String getMatch_timestamp() {
        return match_timestamp;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public String getJibunAddress() {
        return jibunAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStatus() {
        return status;
    }

    public Integer getaTeamScore() {
        return aTeamScore;
    }

    public Integer getbTeamScore() {
        return bTeamScore;
    }

    public String getCreateTimestamp() {
        return createTimestamp;
    }
}
