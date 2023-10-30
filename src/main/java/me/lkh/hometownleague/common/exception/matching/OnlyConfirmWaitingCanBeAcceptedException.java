package me.lkh.hometownleague.common.exception.matching;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.HometownLeagueBaseException;

public class OnlyConfirmWaitingCanBeAcceptedException extends HometownLeagueBaseException {
    public OnlyConfirmWaitingCanBeAcceptedException() {
        super(ErrorCode.ONLY_CONFIRM_WAITING_CAN_BE_ACCEPTED);
    }

    public OnlyConfirmWaitingCanBeAcceptedException(String message) {
        super(ErrorCode.ONLY_CONFIRM_WAITING_CAN_BE_ACCEPTED, message);
    }

    public OnlyConfirmWaitingCanBeAcceptedException(String message, Throwable cause) {
        super(ErrorCode.ONLY_CONFIRM_WAITING_CAN_BE_ACCEPTED, message, cause);
    }

    public OnlyConfirmWaitingCanBeAcceptedException(Throwable cause) {
        super(ErrorCode.ONLY_CONFIRM_WAITING_CAN_BE_ACCEPTED, cause);
    }

    public OnlyConfirmWaitingCanBeAcceptedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCode.ONLY_CONFIRM_WAITING_CAN_BE_ACCEPTED, message, cause, enableSuppression, writableStackTrace);
    }
}
