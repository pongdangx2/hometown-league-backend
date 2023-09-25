package me.lkh.hometownleague.common.exception.team;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class NoSuchPlayerException extends HometownLeagueBaseException {
    public NoSuchPlayerException() {
        super(ErrorCode.NO_SUCH_PLAYER);
    }

    public NoSuchPlayerException(String message) {
        super(ErrorCode.NO_SUCH_PLAYER, message);
    }

    public NoSuchPlayerException(String message, Throwable cause) {
        super(ErrorCode.NO_SUCH_PLAYER, message, cause);
    }

    public NoSuchPlayerException(Throwable cause) {
        super(ErrorCode.NO_SUCH_PLAYER, cause);
    }

    public NoSuchPlayerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.NO_SUCH_PLAYER, message, cause, enableSuppression, writableStackTrace);
    }
}
