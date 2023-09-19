package me.lkh.hometownleague.common.exception.matching;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class MatchingAlreadyExistException extends HometownLeagueBaseException {
    public MatchingAlreadyExistException() {
        super(ErrorCode.MATCHING_ALREADY_EXIST);
    }

    public MatchingAlreadyExistException(String message) {
        super(ErrorCode.MATCHING_ALREADY_EXIST, message);
    }

    public MatchingAlreadyExistException(String message, Throwable cause) {
        super(ErrorCode.MATCHING_ALREADY_EXIST, message, cause);
    }

    public MatchingAlreadyExistException(Throwable cause) {
        super(ErrorCode.MATCHING_ALREADY_EXIST, cause);
    }

    public MatchingAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.MATCHING_ALREADY_EXIST, message, cause, enableSuppression, writableStackTrace);
    }
}
