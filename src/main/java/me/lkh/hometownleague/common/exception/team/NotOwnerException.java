package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class NotOwnerException extends HometownLeagueBaseException {
    public NotOwnerException() {
        super(ErrorCode.NOT_OWNER);
    }

    public NotOwnerException(String message) {
        super(ErrorCode.NOT_OWNER, message);
    }

    public NotOwnerException(String message, Throwable cause) {
        super(ErrorCode.NOT_OWNER, message, cause);
    }

    public NotOwnerException(Throwable cause) {
        super(ErrorCode.NOT_OWNER, cause);
    }

    public NotOwnerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.NOT_OWNER, message, cause, enableSuppression, writableStackTrace);
    }
}
