package me.lkh.hometownleague.common.exception.matching;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class MatchingRequestAlreadyProcessedException extends HometownLeagueBaseException {
    public MatchingRequestAlreadyProcessedException() {
        super(ErrorCode.MATCHING_REQUEST_ALREADY_PROCESSED);
    }

    public MatchingRequestAlreadyProcessedException(String message) {
        super(ErrorCode.MATCHING_REQUEST_ALREADY_PROCESSED, message);
    }

    public MatchingRequestAlreadyProcessedException(String message, Throwable cause) {
        super(ErrorCode.MATCHING_REQUEST_ALREADY_PROCESSED, message, cause);
    }

    public MatchingRequestAlreadyProcessedException(Throwable cause) {
        super(ErrorCode.MATCHING_REQUEST_ALREADY_PROCESSED, cause);
    }

    public MatchingRequestAlreadyProcessedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.MATCHING_REQUEST_ALREADY_PROCESSED, message, cause, enableSuppression, writableStackTrace);
    }
}
