package me.lkh.hometownleague.common.exception.matching;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class CannotFindOtherTeamRequestIdException extends HometownLeagueBaseException {
    public CannotFindOtherTeamRequestIdException() {
        super(ErrorCode.CANNOT_FIND_OTHER_TEAM_REQUEST_ID);
    }

    public CannotFindOtherTeamRequestIdException(String message) {
        super(ErrorCode.CANNOT_FIND_OTHER_TEAM_REQUEST_ID, message);
    }

    public CannotFindOtherTeamRequestIdException(String message, Throwable cause) {
        super(ErrorCode.CANNOT_FIND_OTHER_TEAM_REQUEST_ID, message, cause);
    }

    public CannotFindOtherTeamRequestIdException(Throwable cause) {
        super(ErrorCode.CANNOT_FIND_OTHER_TEAM_REQUEST_ID, cause);
    }

    public CannotFindOtherTeamRequestIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.CANNOT_FIND_OTHER_TEAM_REQUEST_ID, message, cause, enableSuppression, writableStackTrace);
    }
}
