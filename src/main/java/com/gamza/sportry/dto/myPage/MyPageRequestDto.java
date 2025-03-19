package com.gamza.sportry.dto.myPage;

import com.gamza.sportry.entity.custom.Gender;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyPageRequestDto {
    private String id;
    private String profileUrl;
    private LocalDate birthday;
    private Gender gender;
    private String nickName;
}
