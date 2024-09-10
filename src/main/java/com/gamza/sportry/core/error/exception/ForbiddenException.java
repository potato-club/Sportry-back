package com.gamza.sportry.core.error.exception;
import com.gamza.sportry.core.error.ErrorCode;

public class ForbiddenException extends BusinessException {

    public ForbiddenException(String message,ErrorCode errorCode) {
        super(message, errorCode);
    }
}
