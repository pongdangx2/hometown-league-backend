package me.lkh.hometownleague.team.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TeamPlayLocation {

    private final Integer id;

    @JsonIgnore
    private final Integer teamId;

    private final double latitude;    // 위도

    private final double longitude;   // 경도

    private final String legalCode;   // 법정동코드

    private final String jibunAddress;    // 지번주소

    private final String roadAddress; // 도로명주소

    public static TeamPlayLocation forInsertTeamPlayLocation(Integer teamId, double latitude, double longitude, String legalCode, String jibunAddress, String roadAddress){
        return new TeamPlayLocation(null, teamId, jibunAddress, roadAddress, latitude, longitude, legalCode);
    }
    public TeamPlayLocation(Integer id, Integer teamId, String jibunAddress, String roadAddress, double latitude, double longitude, String legalCode) {
        this.id = id;
        this.teamId = teamId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.legalCode = legalCode;
        this.jibunAddress = jibunAddress;
        this.roadAddress = roadAddress;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLegalCode() {
        return legalCode;
    }

    public String getJibunAddress() {
        return jibunAddress;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    @Override
    public String toString() {
        return "TeamPlayLocation{" +
                "teamId='" + teamId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", legalCode='" + legalCode + '\'' +
                ", jibunAddress='" + jibunAddress + '\'' +
                ", roadAddress='" + roadAddress + '\'' +
                '}';
    }
}
