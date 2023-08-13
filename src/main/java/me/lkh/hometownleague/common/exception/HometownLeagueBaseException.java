package me.lkh.hometownleague.common.exception;

public class HometownLeagueBaseException extends RuntimeException {

    private final ErrorCode errorCode;

    public HometownLeagueBaseException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public HometownLeagueBaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public HometownLeagueBaseException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public HometownLeagueBaseException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    protected HometownLeagueBaseException(ErrorCode errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
