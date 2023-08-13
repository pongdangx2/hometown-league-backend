package me.lkh.hometownleague.user.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

public class User {

    private final String id;

    private final String name;

//    @JsonIgnore
    private final String password;

    @JsonIgnore
    private final String useYn;

    private final LocalDateTime createTimestamp;

    private final LocalDateTime modifiedTimeStamp;

    public User(String id, String name, String password, String useYn, LocalDateTime createTimestamp, LocalDateTime modifiedTimeStamp) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.useYn = useYn;
        this.createTimestamp = createTimestamp;
        this.modifiedTimeStamp = modifiedTimeStamp;
    }

    @ConstructorProperties({"id", "name", "password"})
    public User(String id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
        this.useYn = null;
        this.createTimestamp = null;
        this.modifiedTimeStamp = null;
    }

    public User(String id, String name){
        this.id = id;
        this.name = name;
        this.password = null;
        this.useYn = null;
        this.createTimestamp = null;
        this.modifiedTimeStamp = null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUseYn() {
        return useYn;
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
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", useYn='" + useYn + '\'' +
                ", createTimestamp=" + createTimestamp +
                ", modifiedTimeStamp=" + modifiedTimeStamp +
                '}';
    }
}
