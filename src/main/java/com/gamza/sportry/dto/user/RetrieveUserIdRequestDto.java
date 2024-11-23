package com.gamza.sportry.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RetrieveUserIdRequestDto {
    private String nickName;
    private String userEmail;
}
