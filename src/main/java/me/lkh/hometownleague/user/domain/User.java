package me.lkh.hometownleague.user.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class User {

    private final String id;

    private final String nickname;

    @JsonIgnore
    private final String password;

    private final String description;

    @JsonIgnore
    private final String useYn;

    @JsonIgnore
    private final LocalDateTime createTimestamp;

    @JsonIgnore
    private final LocalDateTime modifiedTimestamp;

    public User(String id, String nickname, String password, String useYn, String description, LocalDateTime createTimestamp, LocalDateTime modifiedTimestamp) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.description = description;
        this.useYn = useYn;
        this.createTimestamp = createTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public User(String id, String nickname, String password){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.description = "";
        this.useYn = null;
        this.createTimestamp = null;
        this.modifiedTimestamp = null;
    }

    public User(String id, String nickname, String password, String description){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.description = description;
        this.useYn = null;
        this.createTimestamp = null;
        this.modifiedTimestamp = null;
    }

    public User(String id, String password){
        this.id = id;
        this.nickname = null;
        this.password = password;
        this.description = "";
        this.useYn = null;
        this.createTimestamp = null;
        this.modifiedTimestamp = null;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getUseYn() {
        return useYn;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreateTimestamp() {
        return createTimestamp;
    }

    public LocalDateTime getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", useYn='" + useYn + '\'' +
                ", createTimestamp=" + createTimestamp +
                ", modifiedTimeStamp=" + modifiedTimestamp +
                '}';
    }
}
