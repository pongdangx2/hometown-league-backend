package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class AlreadyJoinedTeamException extends HometownLeagueBaseException {
    public AlreadyJoinedTeamException() {
        super(ErrorCode.ALREADY_JOINED_TEAM);
    }

    public AlreadyJoinedTeamException(String message) {
        super(ErrorCode.ALREADY_JOINED_TEAM, message);
    }

    public AlreadyJoinedTeamException(String message, Throwable cause) {
        super(ErrorCode.ALREADY_JOINED_TEAM, message, cause);
    }

    public AlreadyJoinedTeamException(Throwable cause) {
        super(ErrorCode.ALREADY_JOINED_TEAM, cause);
    }

    public AlreadyJoinedTeamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.ALREADY_JOINED_TEAM, message, cause, enableSuppression, writableStackTrace);
    }
}
