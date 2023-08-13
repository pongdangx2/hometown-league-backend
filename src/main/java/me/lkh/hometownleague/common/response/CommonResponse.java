package me.lkh.hometownleague.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
import me.lkh.hometownleague.common.exception.ErrorCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse <T> {
    private final T data;

    //@JsonProperty("response_code")
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
