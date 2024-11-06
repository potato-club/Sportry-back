package com.gamza.sportry.dto.main.response;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class MainHotPostResponseDto {

    private final long id;
    private final String title;
    private final int viewCount;
    private final int postLikes;
    private final int commentCounts;

}