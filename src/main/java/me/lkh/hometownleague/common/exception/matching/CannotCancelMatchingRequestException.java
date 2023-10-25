package me.lkh.hometownleague.common.exception.matching;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class CannotCancelMatchingRequestException extends HometownLeagueBaseException {
    public CannotCancelMatchingRequestException() {
        super(ErrorCode.CANNOT_CANCEL_MATCHING_REQUEST);
    }

    public CannotCancelMatchingRequestException(String message) {
        super(ErrorCode.CANNOT_CANCEL_MATCHING_REQUEST, message);
    }

    public CannotCancelMatchingRequestException(String message, Throwable cause) {
        super(ErrorCode.CANNOT_CANCEL_MATCHING_REQUEST, message, cause);
    }

    public CannotCancelMatchingRequestException(Throwable cause) {
        super(ErrorCode.CANNOT_CANCEL_MATCHING_REQUEST, cause);
    }

    public CannotCancelMatchingRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.CANNOT_CANCEL_MATCHING_REQUEST, message, cause, enableSuppression, writableStackTrace);
    }
}
