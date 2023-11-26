package me.lkh.hometownleague.common.exception.image;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class CannotSaveFileException extends HometownLeagueBaseException {
    public CannotSaveFileException() {
        super(ErrorCode.CANNOT_SAVE_FILE);
    }

    public CannotSaveFileException(String message) {
        super(ErrorCode.CANNOT_SAVE_FILE, message);
    }

    public CannotSaveFileException(String message, Throwable cause) {
        super(ErrorCode.CANNOT_SAVE_FILE, message, cause);
    }

    public CannotSaveFileException(Throwable cause) {
        super(ErrorCode.CANNOT_SAVE_FILE, cause);
    }

    public CannotSaveFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.CANNOT_SAVE_FILE, message, cause, enableSuppression, writableStackTrace);
    }
}
