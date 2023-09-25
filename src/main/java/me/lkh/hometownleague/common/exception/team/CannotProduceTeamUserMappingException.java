package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class CannotProduceTeamUserMappingException extends HometownLeagueBaseException {
    public CannotProduceTeamUserMappingException() {
        super(ErrorCode.CANNOT_PRODUCE_TEAM_USER_MAPPING);
    }

    public CannotProduceTeamUserMappingException(String message) {
        super(ErrorCode.CANNOT_PRODUCE_TEAM_USER_MAPPING, message);
    }

    public CannotProduceTeamUserMappingException(String message, Throwable cause) {
        super(ErrorCode.CANNOT_PRODUCE_TEAM_USER_MAPPING, message, cause);
    }

    public CannotProduceTeamUserMappingException(Throwable cause) {
        super(ErrorCode.CANNOT_PRODUCE_TEAM_USER_MAPPING, cause);
    }

    public CannotProduceTeamUserMappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.CANNOT_PRODUCE_TEAM_USER_MAPPING, message, cause, enableSuppression, writableStackTrace);
    }
}
