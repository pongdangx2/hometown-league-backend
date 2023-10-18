package me.lkh.hometownleague.schedule.matching.domain;

public class TeamMatchingRequestMapping {
    private final Integer aTeamRequestId;
    private final Integer bTeamRequestId;
    private final String matchTimestamp;
    private final String roadAddress;
    private final String jibunAddress;
    private final Double latitude;
    private final Double longitude;

    public TeamMatchingRequestMapping(Integer aTeamRequestId, Integer bTeamRequestId, String matchTimestamp, String roadAddress, String jibunAddress, Double latitude, Double longitude) {
        this.aTeamRequestId = aTeamRequestId;
        this.bTeamRequestId = bTeamRequestId;
        this.matchTimestamp = matchTimestamp;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getaTeamRequestId() {
        return aTeamRequestId;
    }

    public Integer getbTeamRequestId() {
        return bTeamRequestId;
    }

    public String getMatchTimestamp() {
        return matchTimestamp;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public String getJibunAddress() {
        return jibunAddress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

}
