package me.lkh.hometownleague.user.domain;


public class User {

    private String id;

    private String name;

    private String password;

    private String useYn;


    public User(String id, String name, String password, String useYn) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.useYn = useYn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
