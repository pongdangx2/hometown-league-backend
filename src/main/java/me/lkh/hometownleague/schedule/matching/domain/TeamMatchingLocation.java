package me.lkh.hometownleague.schedule.matching.domain;

public class TeamMatchingLocation {
    private final Integer teamId;
    private final Double latitude;
    private final Double longitude;
    private final Integer legalCode;

    public TeamMatchingLocation(Integer teamId, Double latitude, Double longitude, Integer legalCode) {
        this.teamId = teamId;
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
}
