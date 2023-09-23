package me.lkh.hometownleague.team.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamPlayLocation {

    private final Integer id;

    private final String name;  //  장소명

    @JsonIgnore
    private final Integer teamId;

    private final double latitude;    // 위도

    private final double longitude;   // 경도

    private final String legalCode;   // 법정동코드

    private final String jibunAddress;    // 지번주소

    private final String roadAddress; // 도로명주소

    public static TeamPlayLocation forInsertTeamPlayLocation(Integer teamId, String name, double latitude, double longitude, String legalCode, String jibunAddress, String roadAddress){
        return new TeamPlayLocation(null, name, teamId, latitude, longitude, legalCode, jibunAddress, roadAddress);
    }

    public TeamPlayLocation(Integer id, String name, Integer teamId, double latitude, double longitude, String legalCode, String jibunAddress, String roadAddress) {
        this.id = id;
        this.name = name;
        this.teamId = teamId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.legalCode = legalCode;
        this.jibunAddress = jibunAddress;
        this.roadAddress = roadAddress;
    }

    public String getName() {
        return name;
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
