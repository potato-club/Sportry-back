package com.gamza.sportry.core.error.exception;


import com.gamza.sportry.core.error.ErrorCode;

public class InternalServerException extends BusinessException {

    public InternalServerException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
