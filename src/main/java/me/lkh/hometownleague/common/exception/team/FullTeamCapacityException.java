package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class FullTeamCapacityException extends HometownLeagueBaseException {
    public FullTeamCapacityException() {
        super(ErrorCode.FULL_TEAM_CAPACITY);
    }

    public FullTeamCapacityException(String message) {
        super(ErrorCode.FULL_TEAM_CAPACITY, message);
    }

    public FullTeamCapacityException(String message, Throwable cause) {
        super(ErrorCode.FULL_TEAM_CAPACITY, message, cause);
    }

    public FullTeamCapacityException(Throwable cause) {
        super(ErrorCode.FULL_TEAM_CAPACITY, cause);
    }

    public FullTeamCapacityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.FULL_TEAM_CAPACITY, message, cause, enableSuppression, writableStackTrace);
    }
}
