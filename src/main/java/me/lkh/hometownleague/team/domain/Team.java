package me.lkh.hometownleague.team.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.servlet.http.HttpServletRequest;
import me.lkh.hometownleague.team.domain.request.MakeTeamRequest;

import java.time.LocalDateTime;

public class Team {

    private final Integer id;

    private final String name;

    private final String ownerId;

    private final String ciPath;

    private final String description;

    private final Integer rankScore;

    private final Integer kind;

    @JsonIgnore
    private final String useYn;

    @JsonIgnore
    private final LocalDateTime createTimestamp;

    @JsonIgnore
    private final LocalDateTime modifiedTimestamp;


    /**
     * 팀 생성 API를 위한 Team 생성 용도
     * @param name
     * @param ownerId
     * @param ciPath
     * @param description
     * @param kind
     * @return
     * @see me.lkh.hometownleague.team.controller.TeamController#makeTeam(MakeTeamRequest, HttpServletRequest)
     */
    public static Team forCreatingTeam(String name, String ownerId, String ciPath, String description, int kind){
        return new Team(null, name, ownerId, ciPath, description, null,  kind, "Y", null, null);
    }
    public static Team forOwnerCheck(int id, String ownerId){
        return new Team(id, null, ownerId, null, null, null,  null, "Y", null, null);
    }

    public Team(Integer id, String name, String ownerId, String ciPath, String description, Integer rankScore, Integer kind, String useYn, LocalDateTime createTimestamp, LocalDateTime modifiedTimestamp) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.ciPath = ciPath;
        this.description = description;
        this.rankScore = rankScore;
        this.kind = kind;
        this.useYn = useYn;
        this.createTimestamp = createTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public Integer getId() {
        return id;
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

    public Integer getRankScore() {
        return rankScore;
    }

    public Integer getKind() {
        return kind;
    }

    public String getUseYn() {
        return useYn;
    }

    public LocalDateTime getCreateTimestamp() {
        return createTimestamp;
    }

    public LocalDateTime getModifiedTimestamp() {
        return modifiedTimestamp;
    }
}
