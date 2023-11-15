package me.lkh.hometownleague.matching.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchingListElement {
    private final Integer teamId;
    private final Integer matchingRequestId;
    @JsonIgnore
    private final String processYn;
    private final String name;
    private final Integer rankScore;
    private final Integer kind;
    private final String description;
    private final String status;
    private final String statusName;
    private final String matchTimestamp;

    public MatchingListElement(Integer teamId, Integer matchingRequestId, String processYn, String name, Integer rankScore, Integer kind, String description, String status, String statusName, String matchTimestamp) {
        this.teamId = teamId;
        this.matchingRequestId = matchingRequestId;
        this.name = name;
        this.processYn = processYn;
        this.rankScore = rankScore;
        this.kind = kind;
        this.description = description;
        this.status = status;
        this.statusName = statusName;
        this.matchTimestamp = matchTimestamp;
    }

    public String getProcessYn() {
        return processYn;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Integer getMatchingRequestId() {
        return matchingRequestId;
    }

    public String getName() {
        return name;
    }

    public Integer getRankScore() {
        return rankScore;
    }

    public Integer getKind() {
        return kind;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getMatchTimestamp() {
        return matchTimestamp;
    }
}
