package com.gamza.sportry.core.error;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ErrorEntity {

    private final String errorCode;
    private final String errorMessage;

    @Builder
    public ErrorEntity(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
