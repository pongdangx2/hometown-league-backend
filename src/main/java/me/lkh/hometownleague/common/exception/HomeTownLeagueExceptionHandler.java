package me.lkh.hometownleague.common.exception;

import me.lkh.hometownleague.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HomeTownLeagueExceptionHandler {

    @ExceptionHandler(value = HometownLeagueBaseException.class)
    public ResponseEntity HometownLeagueBaseExceptionHandler(HometownLeagueBaseException hometownLeagueBaseException){
        CommonResponse commonResponse = CommonResponse.withEmptyData(hometownLeagueBaseException.getErrorCode());
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity BaseExceptionHandler(Exception exception){
        exception.printStackTrace();
        CommonResponse commonResponse = CommonResponse.withEmptyData(ErrorCode.COMMON_ERROR);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
