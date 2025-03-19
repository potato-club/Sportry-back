package com.gamza.sportry.dto.myPage;

import com.gamza.sportry.entity.custom.Gender;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponseDto {
    private final String id;
    private final String profileUrl;
    private final LocalDate birthday;
    private final Gender gender;
    private final String nickName;
    private final String email;
}
