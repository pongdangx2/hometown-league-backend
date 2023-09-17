package me.lkh.hometownleague.team.domain;

public class TeamJoinRequestUserProfile {
    private final Integer id;
    private final String description;
    private final String userId;
    private final String nickname;
    private final String profileDescription;

    public TeamJoinRequestUserProfile(Integer id, String description, String userId, String nickname, String profileDescription) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.nickname = nickname;
        this.profileDescription = profileDescription;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileDescription() {
        return profileDescription;
    }
}
