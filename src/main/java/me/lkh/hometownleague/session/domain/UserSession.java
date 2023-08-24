package me.lkh.hometownleague.session.domain;


public class UserSession {
    private String sessionId;
    private String userId;
    private String userName;

    private UserSession() {
    }

    public UserSession(String sessionId, String userId, String userName) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.userName = userName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
