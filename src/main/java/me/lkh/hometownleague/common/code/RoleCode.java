package me.lkh.hometownleague.common.code;

public enum RoleCode {
    OWNER("O")
    ,PLAYER("P");

    private final String roleCode;

    RoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleCode() {
        return roleCode;
    }
}
