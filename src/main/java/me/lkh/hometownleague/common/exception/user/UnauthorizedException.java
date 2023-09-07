package me.lkh.hometownleague.common.exception.user;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

/**
 * 세션이 없는 경우 발생
 * @see ErrorCode
 */
public class UnauthorizedException extends HometownLeagueBaseException {
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(ErrorCode.UNAUTHORIZED, message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(ErrorCode.UNAUTHORIZED, message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(ErrorCode.UNAUTHORIZED, cause);
    }

    public UnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.UNAUTHORIZED, message, cause, enableSuppression, writableStackTrace);
    }
}
