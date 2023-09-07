package me.lkh.hometownleague.common.exception.user;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

/**
 * 세션을 확인할 수 없는 경우 발생
 * @see ErrorCode
 */
public class InvalidSessionException extends HometownLeagueBaseException {
    public InvalidSessionException() {
        super(ErrorCode.INVALID_SESSION);
    }

    public InvalidSessionException(String message) {
        super(ErrorCode.INVALID_SESSION, message);
    }

    public InvalidSessionException(String message, Throwable cause) {
        super(ErrorCode.INVALID_SESSION, message, cause);
    }

    public InvalidSessionException(Throwable cause) {
        super(ErrorCode.INVALID_SESSION, cause);
    }

    public InvalidSessionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.INVALID_SESSION, message, cause, enableSuppression, writableStackTrace);
    }
}
