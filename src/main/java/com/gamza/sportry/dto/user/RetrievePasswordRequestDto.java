package com.gamza.sportry.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RetrievePasswordRequestDto {
    private String userId;
    private String nickName;
    private String userEmail;
}
