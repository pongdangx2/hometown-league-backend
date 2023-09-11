package me.lkh.hometownleague.team.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamPlayTime {

    private final Integer id;

    @JsonIgnore
    private final Integer teamId;

    private final int dayOfWeek;

    private final String playTimeFrom;

    private final String playTimeTo;

    public static TeamPlayTime forInsertTeamPlayTime(Integer teamId, int dayOfWeek, String playTimeFrom, String playTimeTo){
        return new TeamPlayTime(null, teamId, dayOfWeek, playTimeFrom, playTimeTo);
    }
    public TeamPlayTime(Integer id, Integer teamId, int dayOfWeek, String playTimeFrom, String playTimeTo) {
        this.id = id;
        this.teamId = teamId;
        this.dayOfWeek = dayOfWeek;
        this.playTimeFrom = playTimeFrom;
        this.playTimeTo = playTimeTo;
    }

    public Integer getId() {
        return id;
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

    public Integer getTeamId() {
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
