package me.lkh.hometownleague.team.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class Team {

    private final Integer id;

    private final String name;

    private final String ownerId;

    private final String ciPath;

    private final String description;

    private final Integer rankScore;

    private final int kind;

    @JsonIgnore
    private final String useYn;

    @JsonIgnore
    private final LocalDateTime createTimestamp;

    @JsonIgnore
    private final LocalDateTime modifiedTimestamp;

    public static Team forPost(String name, String ownerId, String ciPath, String description, int kind){
        return new Team(null, name, ownerId, ciPath, description, 0,  kind, "Y", null, null);
    }
    public Team(Integer id, String name, String ownerId, String ciPath, String description, Integer rankScore, int kind, String useYn, LocalDateTime createTimestamp, LocalDateTime modifiedTimestamp) {
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

    public int getKind() {
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
