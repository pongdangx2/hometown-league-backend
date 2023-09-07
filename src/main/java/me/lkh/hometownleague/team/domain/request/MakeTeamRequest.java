package me.lkh.hometownleague.team.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.lkh.hometownleague.team.domain.TeamPlayLocation;
import me.lkh.hometownleague.team.domain.TeamPlayTime;

import java.util.List;

public class MakeTeamRequest {

    private final String name;
    @JsonIgnore
    private final String ownerId;
    @JsonIgnore
    private final String ciPath;
    private final String description;
    @JsonIgnore
    private final Integer score;
    private final int kind;
    private final List<TeamPlayTime> time;
    private final List<TeamPlayLocation> location;

    public MakeTeamRequest(String name, String ownerId, String ciPath, String description, Integer score, int kind, List<TeamPlayTime> time, List<TeamPlayLocation> location) {
        this.name = name;
        this.ownerId = ownerId;
        this.ciPath = ciPath;
        this.description = description;
        this.score = score;
        this.kind = kind;
        this.time = time;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getCiPath() {
        return ciPath;
    }

    public String getDescription() {
        return description;
    }

    public Integer getScore() {
        return score;
    }

    public int getKind() {
        return kind;
    }

    public List<TeamPlayTime> getTime() {
        return time;
    }

    public List<TeamPlayLocation> getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "MakeTeamRequest{" +
                "name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", ciPath='" + ciPath + '\'' +
                ", description='" + description + '\'' +
                ", score=" + score +
                ", kind=" + kind +
                '}';
    }
}
