package me.lkh.hometownleague.team.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import me.lkh.hometownleague.team.domain.request.MakeTeamRequest;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Team {

    private final Integer id;

    private final String name;

    private final String ownerId;

    private final String ciPath;

    private final String description;

    private final Integer rankScore;

    private final String rank;

    private final Integer kind;

    private final String ownerYn;

    private final List<TeamPlayTime> time;

    private final List<TeamPlayLocation> location;

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
        return new Team(null, name, ownerId, ciPath, description, 0, null, kind, null,null, null, "Y", null, null);
    }
    public static Team forOwnerCheck(Integer id, String ownerId){
        return new Team(id, null, ownerId, null, null, null, null, null, null,null, null, "Y", null, null);
    }
    public static Team forSelectTeamResponse(Integer id, String name, String ciPath, String description, Integer rankScore, String rank, Integer kind, String ownerId, List<TeamPlayTime> time, List<TeamPlayLocation> location){
        return new Team(id, name, ownerId, ciPath, description, rankScore, rank, kind, null, time, location, null, null, null);
    }
    public static Team forUpdateTeam(Integer id, String name, String description){
        return new Team(id, name, null, null, description, null, null, null, null, null, null, null, null, null);
    }

    /**
     * selectTeam 쿼리를 위한 생성자(Mybatis)
     * @param id
     * @param name
     * @param ciPath
     * @param description
     * @param rankScore
     * @param kind
     * @param ownerId
     */
    public Team(Integer id, String name, String ciPath, String description, Integer rankScore, Integer kind, String ownerId){
        this(id, name, ownerId, ciPath, description, rankScore, null, kind, null, null, null, null, null, null);
    }

    /**
     *
     * selectTeamList 쿼리를 위한 생성자(Mybatis)
     * @param id
     * @param name
     * @param ciPath
     * @param description
     * @param rankScore
     * @param kind
     */
    public Team(Integer id, String name, String ciPath, String description, Integer rankScore, Integer kind){
        this(id, name, null, ciPath, description,rankScore, null, kind, null, null, null,null, null, null);
    }

    public Team(Integer id, String name, String ownerId, String ciPath, String description, Integer rankScore, String rank, Integer kind, String ownerYn, List<TeamPlayTime> time, List<TeamPlayLocation> location, String useYn, LocalDateTime createTimestamp, LocalDateTime modifiedTimestamp) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.ciPath = ciPath;
        this.description = description;
        this.rankScore = rankScore;
        this.rank = rank;
        this.kind = kind;
        this.ownerYn = ownerYn;
        this.time = time;
        this.location = location;
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

    public String getOwnerYn() {
        return ownerYn;
    }

    public List<TeamPlayTime> getTime() {
        return time;
    }

    public List<TeamPlayLocation> getLocation() {
        return location;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", ciPath='" + ciPath + '\'' +
                ", description='" + description + '\'' +
                ", rankScore=" + rankScore +
                ", kind=" + kind +
                ", ownerYn='" + ownerYn + '\'' +
                ", time=" + time +
                ", location=" + location +
                ", useYn='" + useYn + '\'' +
                ", createTimestamp=" + createTimestamp +
                ", modifiedTimestamp=" + modifiedTimestamp +
                '}';
    }
}
