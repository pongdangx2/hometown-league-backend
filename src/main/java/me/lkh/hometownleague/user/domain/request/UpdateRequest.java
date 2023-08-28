package me.lkh.hometownleague.user.domain.request;

public class UpdateRequest {
    private String id;
    private String nickname;
    private String password;
    private String description;

    public UpdateRequest(String id, String nickname, String password, String description) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}
