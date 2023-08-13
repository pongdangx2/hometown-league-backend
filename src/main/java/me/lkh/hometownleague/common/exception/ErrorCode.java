package me.lkh.hometownleague.common.exception;

public enum ErrorCode {
     SUCCESS("0000", "성공")
    ,DUPLICATE_ID("1000", "이미 존재하는 ID")
    ,DUPLICATE_NAME("1001", "이미 존재하는 닉네임")
    ,COMMON_ERROR("9000", "기타 에러")
    ;

    private String code;
    private String message;

    ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }

}
