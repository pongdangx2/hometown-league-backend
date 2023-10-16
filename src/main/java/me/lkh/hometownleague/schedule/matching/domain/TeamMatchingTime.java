package me.lkh.hometownleague.schedule.matching.domain;

public class TeamMatchingTime {
    private final Integer teamId;
    private final String fromTime;
    private final String toTime;
    private final Integer dayOfWeek;

    public TeamMatchingTime(Integer teamId, String fromTime, String toTime, Integer dayOfWeek) {
        this.teamId = teamId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }
}
