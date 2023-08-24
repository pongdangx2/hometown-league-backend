package me.lkh.hometownleague.user.domain.request;


public class LoginRequest {

    private String id;

    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
