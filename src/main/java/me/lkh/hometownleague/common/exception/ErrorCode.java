package me.lkh.hometownleague.common.exception;

public enum ErrorCode {
     SUCCESS("00", "성공")
    ,COMMON_ERROR("99", "기타 에러")
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
