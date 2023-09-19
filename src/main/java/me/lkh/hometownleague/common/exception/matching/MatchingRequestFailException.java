package me.lkh.hometownleague.common.exception.matching;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class MatchingRequestFailException extends HometownLeagueBaseException {
    public MatchingRequestFailException() {
        super(ErrorCode.MATCHING_REQUEST_FAIL);
    }

    public MatchingRequestFailException(String message) {
        super(ErrorCode.MATCHING_REQUEST_FAIL, message);
    }

    public MatchingRequestFailException(String message, Throwable cause) {
        super(ErrorCode.MATCHING_REQUEST_FAIL, message, cause);
    }

    public MatchingRequestFailException(Throwable cause) {
        super(ErrorCode.MATCHING_REQUEST_FAIL, cause);
    }

    public MatchingRequestFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.MATCHING_REQUEST_FAIL, message, cause, enableSuppression, writableStackTrace);
    }
}
