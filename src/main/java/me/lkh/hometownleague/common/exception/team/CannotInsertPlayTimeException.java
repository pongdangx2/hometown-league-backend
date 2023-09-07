package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class CannotInsertPlayTimeException extends HometownLeagueBaseException {
    public CannotInsertPlayTimeException() {
        super(ErrorCode.CANNOT_INSERT_PLAY_TIME);
    }

    public CannotInsertPlayTimeException(String message) {
        super(ErrorCode.CANNOT_INSERT_PLAY_TIME, message);
    }

    public CannotInsertPlayTimeException(String message, Throwable cause) {
        super(ErrorCode.CANNOT_INSERT_PLAY_TIME, message, cause);
    }

    public CannotInsertPlayTimeException(Throwable cause) {
        super(ErrorCode.CANNOT_INSERT_PLAY_TIME, cause);
    }

    public CannotInsertPlayTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.CANNOT_INSERT_PLAY_TIME, message, cause, enableSuppression, writableStackTrace);
    }
}
