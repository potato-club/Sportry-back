package com.gamza.sportry.core.error.exception;


import com.gamza.sportry.core.error.ErrorCode;

public class DuplicateException extends BusinessException {

    public DuplicateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
