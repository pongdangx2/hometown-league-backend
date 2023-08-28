package me.lkh.hometownleague.common.response;

import me.lkh.hometownleague.common.exception.ErrorCode;

/**
 * API의 응답코드
 */
public class ResponseCode {
    private final String code;
    private final String message;

    public ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public ResponseCode(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public String getCode(){
        return this.code;
    }
    public String getMessage(){
        return this.message;
    }
}
