package me.lkh.hometownleague.user.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class User {

    private final String id;

    private final String nickname;

    private final String password;

    private final String description;

    @JsonIgnore
    private final String useYn;

    private final LocalDateTime createTimestamp;

    private final LocalDateTime modifiedTimeStamp;

    public User(String id, String nickname, String password, String useYn, String description, LocalDateTime createTimestamp, LocalDateTime modifiedTimeStamp) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.description = description;
        this.useYn = useYn;
        this.createTimestamp = createTimestamp;
        this.modifiedTimeStamp = modifiedTimeStamp;
    }

    public User(String id, String nickname, String password){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.description = "";
        this.useYn = null;
        this.createTimestamp = null;
        this.modifiedTimeStamp = null;
    }

    public User(String id, String nickname, String password, String description){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.description = description;
        this.useYn = null;
        this.createTimestamp = null;
        this.modifiedTimeStamp = null;
    }

    public User(String id, String password){
        this.id = id;
        this.nickname = null;
        this.password = password;
        this.description = "";
        this.useYn = null;
        this.createTimestamp = null;
        this.modifiedTimeStamp = null;
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

    public LocalDateTime getModifiedTimeStamp() {
        return modifiedTimeStamp;
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
                ", modifiedTimeStamp=" + modifiedTimeStamp +
                '}';
    }
}
