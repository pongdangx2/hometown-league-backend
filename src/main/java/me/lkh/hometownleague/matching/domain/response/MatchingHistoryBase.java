package me.lkh.hometownleague.matching.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MatchingHistoryBase {

    private final Integer id;

    @JsonIgnore
    private final Integer aTeamId;

    @JsonIgnore
    private final Integer bTeamId;

    private final String matchTimestamp;

    private final String roadAddress;

    private final String jibunAddress;

    private final double latitude;

    private final double longitude;

    private final String status;

    private final String statusName;

    @JsonIgnore
    private final Integer aTeamScore;

    @JsonIgnore
    private final Integer bTeamScore;

    @JsonIgnore
    private final String createTimestamp;

    private final MatchingHistoryTeam ourTeam;

    private final MatchingHistoryTeam otherTeam;

    public MatchingHistoryBase addOurTeam(MatchingHistoryTeam ourTeam) {
        return new MatchingHistoryBase(this.id,
                this.aTeamId,
                this.bTeamId,
                this.matchTimestamp,
                this.roadAddress,
                this.jibunAddress,
                this.latitude,
                this.longitude,
                this.status,
                this.statusName,
                this.aTeamScore,
                this.bTeamScore,
                this.createTimestamp,
                ourTeam,
                this.otherTeam);
    }
    public MatchingHistoryBase addOtherTeam(MatchingHistoryTeam otherTeam) {
        return new MatchingHistoryBase(this.id,
                this.aTeamId,
                this.bTeamId,
                this.matchTimestamp,
                this.roadAddress,
                this.jibunAddress,
                this.latitude,
                this.longitude,
                this.status,
                this.statusName,
                this.aTeamScore,
                this.bTeamScore,
                this.createTimestamp,
                this.ourTeam,
                otherTeam);
    }

    public MatchingHistoryBase(Integer id, Integer aTeamId, Integer bTeamId, String matchTimestamp, String roadAddress, String jibunAddress, double latitude, double longitude, String status, String statusName, Integer aTeamScore, Integer bTeamScore, String createTimestamp) {
        this(id, aTeamId, bTeamId, matchTimestamp, roadAddress, jibunAddress, latitude, longitude, status, statusName, aTeamScore, bTeamScore, createTimestamp, null, null);
    }

    public MatchingHistoryBase(Integer id, Integer aTeamId, Integer bTeamId, String matchTimestamp, String roadAddress, String jibunAddress, double latitude, double longitude, String status, String statusName, Integer aTeamScore, Integer bTeamScore, String createTimestamp, MatchingHistoryTeam ourTeam, MatchingHistoryTeam otherTeam) {
        this.id = id;
        this.aTeamId = aTeamId;
        this.bTeamId = bTeamId;
        this.matchTimestamp = matchTimestamp;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.statusName = statusName;
        this.aTeamScore = aTeamScore;
        this.bTeamScore = bTeamScore;
        this.createTimestamp = createTimestamp;
        this.ourTeam = ourTeam;
        this.otherTeam = otherTeam;
    }

    public Integer getId() {
        return id;
    }

    public Integer getaTeamId() {
        return aTeamId;
    }

    public Integer getbTeamId() {
        return bTeamId;
    }

    public String getMatchTimestamp() {
        return matchTimestamp;
    }

    public String getStatusName() {
        return statusName;
    }

    public MatchingHistoryTeam getOurTeam() {
        return ourTeam;
    }

    public MatchingHistoryTeam getOtherTeam() {
        return otherTeam;
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
