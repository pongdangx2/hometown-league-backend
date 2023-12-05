package me.lkh.hometownleague.domain;

public class CommonCode {
    private final String groupId;
    private final String code;
    private final String codeName;

    public CommonCode() {
        this.groupId = null;
        this.code = null;
        this.codeName = null;
    }

    public CommonCode(String groupId, String code, String codeName) {
        this.groupId = groupId;
        this.code = code;
        this.codeName = codeName;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    @Override
    public String toString() {
        return "CommonCode{" +
                "groupId='" + groupId + '\'' +
                ", code='" + code + '\'' +
                ", codeName='" + codeName + '\'' +
                '}';
    }
}
