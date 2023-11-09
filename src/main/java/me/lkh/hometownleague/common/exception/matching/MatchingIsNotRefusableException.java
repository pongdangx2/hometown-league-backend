package me.lkh.hometownleague.common.exception.matching;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class MatchingIsNotRefusableException extends HometownLeagueBaseException {
    public MatchingIsNotRefusableException() {
        super(ErrorCode.MATCHING_IS_NOT_REFUSABLE);
    }

    public MatchingIsNotRefusableException(String message) {
        super(ErrorCode.MATCHING_IS_NOT_REFUSABLE, message);
    }

    public MatchingIsNotRefusableException(String message, Throwable cause) {
        super(ErrorCode.MATCHING_IS_NOT_REFUSABLE, message, cause);
    }

    public MatchingIsNotRefusableException(Throwable cause) {
        super(ErrorCode.MATCHING_IS_NOT_REFUSABLE, cause);
    }

    public MatchingIsNotRefusableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.MATCHING_IS_NOT_REFUSABLE, message, cause, enableSuppression, writableStackTrace);
    }
}
