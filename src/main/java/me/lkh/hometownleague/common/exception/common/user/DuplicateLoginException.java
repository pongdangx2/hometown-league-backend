package me.lkh.hometownleague.common.exception.common.user;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class DuplicateLoginException extends HometownLeagueBaseException {
    public DuplicateLoginException() {
        super(ErrorCode.DUPLICATE_LOGIN);
    }

    public DuplicateLoginException(String message) {
        super(ErrorCode.DUPLICATE_LOGIN, message);
    }

    public DuplicateLoginException(String message, Throwable cause) {
        super(ErrorCode.DUPLICATE_LOGIN, message, cause);
    }

    public DuplicateLoginException(Throwable cause) {
        super(ErrorCode.DUPLICATE_LOGIN, cause);
    }

    public DuplicateLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.DUPLICATE_LOGIN, message, cause, enableSuppression, writableStackTrace);
    }
}
