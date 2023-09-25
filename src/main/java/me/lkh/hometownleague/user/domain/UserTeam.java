package me.lkh.hometownleague.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 유저가 속한 팀의 목록을 위한 클래스
 */
public class UserTeam {
    private final Integer id;
    private final String name;
    @JsonIgnore
    private final String ciPath;
    private final String ownerYn;

    public UserTeam(Integer id, String name, String ciPath, String ownerYn) {
        this.id = id;
        this.name = name;
        this.ownerYn = ownerYn;
        this.ciPath = ciPath;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwnerYn() {
        return ownerYn;
    }

    public String getCiPath() {
        return ciPath;
    }
}
