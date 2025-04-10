package com.gamza.sportry.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResetPasswordRequestDto {
    private Long id;
    private String newPassword;
}
