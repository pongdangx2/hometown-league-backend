package me.lkh.hometownleague.user.domain;

import java.io.Serializable;

/**
 * Session에서 사용할 객체 (직렬화/역직렬화 필요)
 */
public class UserSession implements Serializable {
    private String userId;
    private String userName;

    public UserSession() {
    }

    public UserSession(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
