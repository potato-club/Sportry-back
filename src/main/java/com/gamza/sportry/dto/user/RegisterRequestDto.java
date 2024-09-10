package com.gamza.sportry.dto.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequestDto {

    private String userName;
    private String nickName;
    private String email;
    private LocalDate birthDay;
    private String userId;
    private String userPw;

}
