package com.gamza.sportry.core.error.exception;


import com.gamza.sportry.core.error.ErrorCode;

public class InvalidTokenException extends BusinessException {

    public InvalidTokenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
