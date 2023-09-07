package me.lkh.hometownleague.team.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TeamPlayTime {
    @JsonIgnore
    private final String teamId;
    private final int dayOfWeek;
    private final String playTimeFrom;
    private final String playTimeTo;

    public TeamPlayTime(String teamId, int dayOfWeek, String playTimeFrom, String playTimeTo) {
        this.teamId = teamId;
        this.dayOfWeek = dayOfWeek;
        this.playTimeFrom = playTimeFrom;
        this.playTimeTo = playTimeTo;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public String getPlayTimeFrom() {
        return playTimeFrom;
    }

    public String getPlayTimeTo() {
        return playTimeTo;
    }

    public String getTeamId() {
        return teamId;
    }

    @Override
    public String toString() {
        return "TeamPlayTime{" +
                "teamId='" + teamId + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", playTimeFrom='" + playTimeFrom + '\'' +
                ", playTimeTo='" + playTimeTo + '\'' +
                '}';
    }
}
