package me.lkh.hometownleague.common.exception;

import me.lkh.hometownleague.common.exception.user.InvalidSessionException;
import me.lkh.hometownleague.common.exception.user.UnauthorizedException;
import me.lkh.hometownleague.common.response.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HomeTownLeagueExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = {UnauthorizedException.class, InvalidSessionException.class})
    public ResponseEntity UnauthorizedExceptionHandler(HometownLeagueBaseException unauthorizedException){
        CommonResponse commonResponse = CommonResponse.withEmptyData(unauthorizedException.getErrorCode());
        unauthorizedException.printStackTrace();
        logger.error("error code: " + unauthorizedException.getErrorCode().getCode() + ", " + unauthorizedException.getErrorCode().getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(commonResponse);
    }

    @ExceptionHandler(value = HometownLeagueBaseException.class)
    public ResponseEntity HometownLeagueBaseExceptionHandler(HometownLeagueBaseException hometownLeagueBaseException){
        CommonResponse commonResponse = CommonResponse.withEmptyData(hometownLeagueBaseException.getErrorCode());
        hometownLeagueBaseException.printStackTrace();
        logger.error("error code: " + hometownLeagueBaseException.getErrorCode().getCode() + ", " + hometownLeagueBaseException.getErrorCode().getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity BaseExceptionHandler(Exception exception){
        exception.printStackTrace();
        CommonResponse commonResponse = CommonResponse.withEmptyData(ErrorCode.COMMON_ERROR);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
