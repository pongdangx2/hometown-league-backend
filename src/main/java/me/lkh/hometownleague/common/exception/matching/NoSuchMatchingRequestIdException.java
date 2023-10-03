package me.lkh.hometownleague.common.exception.matching;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class NoSuchMatchingRequestIdException extends HometownLeagueBaseException {
    public NoSuchMatchingRequestIdException() {
        super(ErrorCode.NO_SUCH_MATCHING_REQUEST_ID);
    }

    public NoSuchMatchingRequestIdException(String message) {
        super(ErrorCode.NO_SUCH_MATCHING_REQUEST_ID, message);
    }

    public NoSuchMatchingRequestIdException(String message, Throwable cause) {
        super(ErrorCode.NO_SUCH_MATCHING_REQUEST_ID, message, cause);
    }

    public NoSuchMatchingRequestIdException(Throwable cause) {
        super(ErrorCode.NO_SUCH_MATCHING_REQUEST_ID, cause);
    }

    public NoSuchMatchingRequestIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.NO_SUCH_MATCHING_REQUEST_ID, message, cause, enableSuppression, writableStackTrace);
    }
}
