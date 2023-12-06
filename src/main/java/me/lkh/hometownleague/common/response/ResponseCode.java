package me.lkh.hometownleague.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import me.lkh.hometownleague.common.exception.ErrorCode;

/**
 * API의 응답코드
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCode {
    private final String code;
    private final String message;
    private final Integer count;

    public ResponseCode(String code, String message) {
        this(code, message, null);
    }

    public ResponseCode(String code, String message, Integer count) {
        this.code = code;
        this.message = message;
        this.count = count;
    }

    public ResponseCode(ErrorCode errorCode){
        this(errorCode.getCode(), errorCode.getMessage(), null);
    }
    public ResponseCode(ErrorCode errorCode, Integer count){
        this(errorCode.getCode(), errorCode.getMessage(), count);
    }

    public String getCode(){
        return this.code;
    }
    public String getMessage(){
        return this.message;
    }
    public Integer getCount(){
        return this.count;
    }
}
