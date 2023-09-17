package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class CannotRequestJoinTeamException extends HometownLeagueBaseException {
    public CannotRequestJoinTeamException() {
        super(ErrorCode.CANNOT_REQUEST_JOIN_TEAM);
    }

    public CannotRequestJoinTeamException(String message) {
        super(ErrorCode.CANNOT_REQUEST_JOIN_TEAM, message);
    }

    public CannotRequestJoinTeamException(String message, Throwable cause) {
        super(ErrorCode.CANNOT_REQUEST_JOIN_TEAM, message, cause);
    }

    public CannotRequestJoinTeamException(Throwable cause) {
        super(ErrorCode.CANNOT_REQUEST_JOIN_TEAM, cause);
    }

    public CannotRequestJoinTeamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.CANNOT_REQUEST_JOIN_TEAM, message, cause, enableSuppression, writableStackTrace);
    }
}
