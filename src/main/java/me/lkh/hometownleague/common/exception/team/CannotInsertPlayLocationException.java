package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class CannotInsertPlayLocationException extends HometownLeagueBaseException {
    public CannotInsertPlayLocationException() {
        super(ErrorCode.CANNOT_INSERT_PLAY_LOCATION);
    }

    public CannotInsertPlayLocationException(String message) {
        super(ErrorCode.CANNOT_INSERT_PLAY_LOCATION, message);
    }

    public CannotInsertPlayLocationException(String message, Throwable cause) {
        super(ErrorCode.CANNOT_INSERT_PLAY_LOCATION, message, cause);
    }

    public CannotInsertPlayLocationException(Throwable cause) {
        super(ErrorCode.CANNOT_INSERT_PLAY_LOCATION, cause);
    }

    public CannotInsertPlayLocationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.CANNOT_INSERT_PLAY_LOCATION, message, cause, enableSuppression, writableStackTrace);
    }
}
