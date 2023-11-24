package me.lkh.hometownleague.matching.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchingHistoryTeam {
    private final Integer id;
    private final String name;
    private final String ciPath;
    private final Integer score;

    public MatchingHistoryTeam(Integer id, String name, String ciPath) {
        this(id, name, ciPath, null);
    }

    public MatchingHistoryTeam(Integer id, String name, String ciPath, Integer score) {
        this.id = id;
        this.name = name;
        this.ciPath = ciPath;
        this.score = score;
    }

    public MatchingHistoryTeam addScore(Integer score){
        return new MatchingHistoryTeam(this.id, this.name, this.ciPath, score);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCiPath() {
        return ciPath;
    }

    public Integer getScore() {
        return score;
    }
}
