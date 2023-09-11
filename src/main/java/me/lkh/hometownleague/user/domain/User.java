package me.lkh.hometownleague.user.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private final String id;

    private final String nickname;

    @JsonIgnore
    private final String password;

    private final String description;

    private final String ciPath;

    @JsonIgnore
    private final String useYn;

    @JsonIgnore
    private final LocalDateTime createTimestamp;

    @JsonIgnore
    private final LocalDateTime modifiedTimestamp;

    public User(String id, String password){
        this(id, null, password);
    }

    public User(String id, String nickname, String password){
        this(id, nickname, password, "");
    }

    public User(String id, String nickname, String password, String description){
        this(id, nickname, password, null, description, null, null, null);
    }

    public User(String id, String nickname, String password, String useYn, String description, LocalDateTime createTimestamp, LocalDateTime modifiedTimestamp, String ciPath) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.description = description;
        this.useYn = useYn;
        this.ciPath = ciPath;
        this.createTimestamp = createTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public String getCiPath() {
        return ciPath;
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
