package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class CannotUpdatePlayLocationException extends HometownLeagueBaseException {
    public CannotUpdatePlayLocationException() {
        super(ErrorCode.CANNOT_UPDATE_PLAY_LOCATION);
    }

    public CannotUpdatePlayLocationException(String message) {
        super(ErrorCode.CANNOT_UPDATE_PLAY_LOCATION, message);
    }

    public CannotUpdatePlayLocationException(String message, Throwable cause) {
        super(ErrorCode.CANNOT_UPDATE_PLAY_LOCATION, message, cause);
    }

    public CannotUpdatePlayLocationException(Throwable cause) {
        super(ErrorCode.CANNOT_UPDATE_PLAY_LOCATION, cause);
    }

    public CannotUpdatePlayLocationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.CANNOT_UPDATE_PLAY_LOCATION, message, cause, enableSuppression, writableStackTrace);
    }
}
