package me.lkh.hometownleague.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import me.lkh.hometownleague.common.exception.ErrorCode;

/**
 * API의 기본 응답
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse <T> {
    private final T data;

    private final ResponseCode responseCode;

    public static CommonResponse withEmptyData(ErrorCode errorCode){
        return new CommonResponse(errorCode);
    }

    private CommonResponse(ErrorCode errorCode){
        this.data = null;
        this.responseCode = new ResponseCode(errorCode);
    }

    public CommonResponse(T data){
        this.data = data;
        this.responseCode = new ResponseCode(ErrorCode.SUCCESS);
    }

    public T getData(){
        return this.data;
    }
    public ResponseCode getResponseCode() {
        return this.responseCode;
    }

}
