package me.lkh.hometownleague.common.exception.exceptions;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class CommonErrorException extends HometownLeagueBaseException {

    public CommonErrorException() {
        super(ErrorCode.COMMON_ERROR);
    }

    public CommonErrorException(String message) {
        super(ErrorCode.COMMON_ERROR, message);
    }

    public CommonErrorException(String message, Throwable cause) {
        super(ErrorCode.COMMON_ERROR, message, cause);
    }

    public CommonErrorException(Throwable cause) {
        super(ErrorCode.COMMON_ERROR, cause);
    }

    public CommonErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.COMMON_ERROR, message, cause, enableSuppression, writableStackTrace);
    }

}
