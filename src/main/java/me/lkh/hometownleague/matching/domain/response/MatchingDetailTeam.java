package me.lkh.hometownleague.matching.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import me.lkh.hometownleague.team.domain.Team;
import me.lkh.hometownleague.user.domain.User;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchingDetailTeam {
    @JsonIgnore
    private final Integer teamId;
    private final String status;
    private final String statusName;
    private final String acceptTimestamp;
    private final Team team;
    private final List<User> players;

    /**
     * Matching Detail에서 팀의 기본정보를 조회할 때 사용하는 생성자(Mybatis)
     * @param teamId
     * @param status
     * @param statusName
     * @param acceptTimestamp
     */
    public MatchingDetailTeam(Integer teamId, String status, String statusName, String acceptTimestamp){
        this(teamId, status, statusName, acceptTimestamp, null, null);
    }

    public MatchingDetailTeam(Integer teamId, String status, String statusName, String acceptTimestamp, Team team, List<User> players) {
        this.teamId = teamId;
        this.status = status;
        this.statusName = statusName;
        this.acceptTimestamp = acceptTimestamp;
        this.team = team;
        this.players = players;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getAcceptTimestamp() {
        return acceptTimestamp;
    }

    public Team getTeam() {
        return team;
    }

    public List<User> getPlayers() {
        return players;
    }
}
