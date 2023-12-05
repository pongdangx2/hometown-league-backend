package me.lkh.hometownleague.domain;

public class CommonCodeKey {
    private final String groupId;
    private final String code;

    public CommonCodeKey(String groupId, String code) {
        this.groupId = groupId;
        this.code = code;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "CommonCodeKey{" +
                "groupId='" + groupId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
