package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class DuplicateTeamNameException extends HometownLeagueBaseException {

    public DuplicateTeamNameException() {
        super(ErrorCode.DUPLICATE_TEAM_NAME);
    }

    public DuplicateTeamNameException(String message) {
        super(ErrorCode.DUPLICATE_TEAM_NAME, message);
    }

    public DuplicateTeamNameException(String message, Throwable cause) {
        super(ErrorCode.DUPLICATE_TEAM_NAME, message, cause);
    }

    public DuplicateTeamNameException(Throwable cause) {
        super(ErrorCode.DUPLICATE_TEAM_NAME, cause);
    }

    public DuplicateTeamNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.DUPLICATE_TEAM_NAME, message, cause, enableSuppression, writableStackTrace);
    }
}
