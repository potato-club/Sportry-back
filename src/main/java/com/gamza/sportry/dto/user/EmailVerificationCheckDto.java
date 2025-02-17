package com.gamza.sportry.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailVerificationCheckDto {
    private String email;
    private String code;
}
