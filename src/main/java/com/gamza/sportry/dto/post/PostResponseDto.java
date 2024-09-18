package com.gamza.sportry.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostResponseDto {
    private String title;
    private String content;
    private boolean state;
    private int viewCount;
    private int likeCount;
    private int commentCount;
}
