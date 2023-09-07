package me.lkh.hometownleague.common.exception.user;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

/**
 * 닉네임이 중복된 경우 발생
 * @see ErrorCode
 */
public class DuplicateNameException extends HometownLeagueBaseException {
    public DuplicateNameException() {
        super(ErrorCode.DUPLICATE_NAME);
    }

    public DuplicateNameException(String message) {
        super(ErrorCode.DUPLICATE_NAME, message);
    }

    public DuplicateNameException(String message, Throwable cause) {
        super(ErrorCode.DUPLICATE_NAME, message, cause);
    }

    public DuplicateNameException(Throwable cause) {
        super(ErrorCode.DUPLICATE_NAME, cause);
    }

    public DuplicateNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.DUPLICATE_NAME, message, cause, enableSuppression, writableStackTrace);
    }
}
