package com.gamza.sportry.dto.user;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String userId;
    private String userPw;
}
