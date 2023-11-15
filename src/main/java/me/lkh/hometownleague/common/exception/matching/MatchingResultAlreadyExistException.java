package me.lkh.hometownleague.common.exception.matching;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class MatchingResultAlreadyExistException extends HometownLeagueBaseException {
    public MatchingResultAlreadyExistException() {
        super(ErrorCode.MATCHING_RESULT_ALREADY_EXIST);
    }

    public MatchingResultAlreadyExistException(String message) {
        super(ErrorCode.MATCHING_RESULT_ALREADY_EXIST, message);
    }

    public MatchingResultAlreadyExistException(String message, Throwable cause) {
        super(ErrorCode.MATCHING_RESULT_ALREADY_EXIST, message, cause);
    }

    public MatchingResultAlreadyExistException(Throwable cause) {
        super(ErrorCode.MATCHING_RESULT_ALREADY_EXIST, cause);
    }

    public MatchingResultAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.MATCHING_RESULT_ALREADY_EXIST, message, cause, enableSuppression, writableStackTrace);
    }
}
