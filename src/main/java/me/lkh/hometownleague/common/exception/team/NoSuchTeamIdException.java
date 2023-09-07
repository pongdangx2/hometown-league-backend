package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class NoSuchTeamIdException extends HometownLeagueBaseException {
    public NoSuchTeamIdException() {
        super(ErrorCode.NO_SUCH_TEAM_ID);
    }

    public NoSuchTeamIdException(String message) {
        super(ErrorCode.NO_SUCH_TEAM_ID, message);
    }

    public NoSuchTeamIdException(String message, Throwable cause) {
        super(ErrorCode.NO_SUCH_TEAM_ID, message, cause);
    }

    public NoSuchTeamIdException(Throwable cause) {
        super(ErrorCode.NO_SUCH_TEAM_ID, cause);
    }

    public NoSuchTeamIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.NO_SUCH_TEAM_ID, message, cause, enableSuppression, writableStackTrace);
    }
}
