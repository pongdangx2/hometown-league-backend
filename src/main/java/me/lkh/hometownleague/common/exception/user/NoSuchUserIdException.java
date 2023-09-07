package me.lkh.hometownleague.common.exception.user;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

/**
 * ID가 존재하지 않는 경우 발생
 * @see ErrorCode
 */
public class NoSuchUserIdException extends HometownLeagueBaseException {
    public NoSuchUserIdException() {
        super(ErrorCode.NO_SUCH_USER_ID);
    }

    public NoSuchUserIdException(String message) {
        super(ErrorCode.NO_SUCH_USER_ID, message);
    }

    public NoSuchUserIdException(String message, Throwable cause) {
        super(ErrorCode.NO_SUCH_USER_ID, message, cause);
    }

    public NoSuchUserIdException(Throwable cause) {
        super(ErrorCode.NO_SUCH_USER_ID, cause);
    }

    public NoSuchUserIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.NO_SUCH_USER_ID, message, cause, enableSuppression, writableStackTrace);
    }
}
