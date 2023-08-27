package me.lkh.hometownleague.user.domain.request;

public class JoinRequest {
    private String id;
    private String nickname;
    private String password;
    private String description;

    public JoinRequest(String id, String nickname, String password, String description) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.description = description;
    }

    public String getDescription() {
        return description;
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

    @Override
    public String toString() {
        return "JoinRequest{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
