package me.lkh.hometownleague.user.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class User {

    private final String id;

    private final String name;

    @JsonIgnore
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


    public String getId() {
        return id;
    }

//    public void setId(String id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public String getPassword() {
        return password;
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getUseYn() {
        return useYn;
    }

//    public void setUseYn(String useYn) {
//        this.useYn = useYn;
//    }

    public LocalDateTime getCreateTimestamp() {
        return createTimestamp;
    }

    public LocalDateTime getModifiedTimeStamp() {
        return modifiedTimeStamp;
    }
}
