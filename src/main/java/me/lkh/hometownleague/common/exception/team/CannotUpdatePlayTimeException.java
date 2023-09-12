package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class CannotUpdatePlayTimeException extends HometownLeagueBaseException {
    public CannotUpdatePlayTimeException() {
        super(ErrorCode.CANNOT_UPDATE_PLAY_TIME);
    }

    public CannotUpdatePlayTimeException(String message) {
        super(ErrorCode.CANNOT_UPDATE_PLAY_TIME, message);
    }

    public CannotUpdatePlayTimeException(String message, Throwable cause) {
        super(ErrorCode.CANNOT_UPDATE_PLAY_TIME, message, cause);
    }

    public CannotUpdatePlayTimeException(Throwable cause) {
        super(ErrorCode.CANNOT_UPDATE_PLAY_TIME, cause);
    }

    public CannotUpdatePlayTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.CANNOT_UPDATE_PLAY_TIME, message, cause, enableSuppression, writableStackTrace);
    }
}
