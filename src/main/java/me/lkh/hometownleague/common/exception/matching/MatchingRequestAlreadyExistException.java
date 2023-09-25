package me.lkh.hometownleague.common.exception.matching;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class MatchingRequestAlreadyExistException extends HometownLeagueBaseException {
    public MatchingRequestAlreadyExistException() {
        super(ErrorCode.MATCHING_REQUEST_ALREADY_EXIST);
    }

    public MatchingRequestAlreadyExistException(String message) {
        super(ErrorCode.MATCHING_REQUEST_ALREADY_EXIST, message);
    }

    public MatchingRequestAlreadyExistException(String message, Throwable cause) {
        super(ErrorCode.MATCHING_REQUEST_ALREADY_EXIST, message, cause);
    }

    public MatchingRequestAlreadyExistException(Throwable cause) {
        super(ErrorCode.MATCHING_REQUEST_ALREADY_EXIST, cause);
    }

    public MatchingRequestAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.MATCHING_REQUEST_ALREADY_EXIST, message, cause, enableSuppression, writableStackTrace);
    }
}
