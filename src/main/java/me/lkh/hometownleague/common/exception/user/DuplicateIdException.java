package me.lkh.hometownleague.common.exception.user;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

/**
 * ID가 중복되는 경우 발생
 * @see ErrorCode
 */
public class DuplicateIdException extends HometownLeagueBaseException {

    public DuplicateIdException() {
        super(ErrorCode.DUPLICATE_ID);
    }

    public DuplicateIdException(String message) {
        super(ErrorCode.DUPLICATE_ID, message);
    }

    public DuplicateIdException(String message, Throwable cause) {
        super(ErrorCode.DUPLICATE_ID, message, cause);
    }

    public DuplicateIdException(Throwable cause) {
        super(ErrorCode.DUPLICATE_ID, cause);
    }

    public DuplicateIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.DUPLICATE_ID, message, cause, enableSuppression, writableStackTrace);
    }
}
