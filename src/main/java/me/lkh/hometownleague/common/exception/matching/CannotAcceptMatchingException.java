package me.lkh.hometownleague.common.exception.matching;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class CannotAcceptMatchingException extends HometownLeagueBaseException {
    public CannotAcceptMatchingException() {
        super(ErrorCode.CANNOT_ACCEPT_MATCHING);
    }

    public CannotAcceptMatchingException(String message) {
        super(ErrorCode.CANNOT_ACCEPT_MATCHING, message);
    }

    public CannotAcceptMatchingException(String message, Throwable cause) {
        super(ErrorCode.CANNOT_ACCEPT_MATCHING, message, cause);
    }

    public CannotAcceptMatchingException(Throwable cause) {
        super(ErrorCode.CANNOT_ACCEPT_MATCHING, cause);
    }

    public CannotAcceptMatchingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.CANNOT_ACCEPT_MATCHING, message, cause, enableSuppression, writableStackTrace);
    }
}
