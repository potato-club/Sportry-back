package com.gamza.sportry.core.error.exception;


import com.gamza.sportry.core.error.ErrorCode;

public class S3Exception extends BusinessException {
    public S3Exception(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
