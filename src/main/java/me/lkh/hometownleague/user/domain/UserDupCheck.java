package me.lkh.hometownleague.user.domain;

public class UserDupCheck {

    // 이름 중복 존재 여부
    private final String nameDupYn;

    // 아이디 중복 존재 여부
    private final String idDupYn;

    public UserDupCheck(String nameDupYn, String idDupYn) {
        this.nameDupYn = nameDupYn;
        this.idDupYn = idDupYn;
    }

    public String getNameDupYn() {
        return nameDupYn;
    }

    public String getIdDupYn() {
        return idDupYn;
    }
}
