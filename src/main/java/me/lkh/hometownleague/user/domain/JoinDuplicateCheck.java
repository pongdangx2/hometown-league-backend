package me.lkh.hometownleague.user.domain;

public class JoinDuplicateCheck {
    private String type;
    private String value;

    public JoinDuplicateCheck(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
