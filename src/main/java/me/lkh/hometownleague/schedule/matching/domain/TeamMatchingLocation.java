package me.lkh.hometownleague.schedule.matching.domain;

public class TeamMatchingLocation {
    private final Integer teamId;
    private final String name;
    private final String roadAddress;
    private final String jibunAddress;
    private final Double latitude;
    private final Double longitude;
    private final Integer legalCode;

    public TeamMatchingLocation(Integer teamId, String name, String roadAddress, String jibunAddress, Double latitude, Double longitude, Integer legalCode) {
        this.teamId = teamId;
        this.name = name;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.legalCode = legalCode;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getLegalCode() {
        return legalCode;
    }

    public String getName() {
        return name;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public String getJibunAddress() {
        return jibunAddress;
    }
}
