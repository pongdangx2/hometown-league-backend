package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class DuplicateTeamJoinRequestException extends HometownLeagueBaseException {
    public DuplicateTeamJoinRequestException() {
        super(ErrorCode.DUPLICATE_TEAM_JOIN_REQUEST);
    }

    public DuplicateTeamJoinRequestException(String message) {
        super(ErrorCode.DUPLICATE_TEAM_JOIN_REQUEST, message);
    }

    public DuplicateTeamJoinRequestException(String message, Throwable cause) {
        super(ErrorCode.DUPLICATE_TEAM_JOIN_REQUEST, message, cause);
    }

    public DuplicateTeamJoinRequestException(Throwable cause) {
        super(ErrorCode.DUPLICATE_TEAM_JOIN_REQUEST, cause);
    }

    public DuplicateTeamJoinRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.DUPLICATE_TEAM_JOIN_REQUEST, message, cause, enableSuppression, writableStackTrace);
    }
}
