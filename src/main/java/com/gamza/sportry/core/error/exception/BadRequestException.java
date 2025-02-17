package com.gamza.sportry.core.error.exception;


import com.gamza.sportry.core.error.ErrorCode;

public class BadRequestException extends BusinessException {

    public BadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
