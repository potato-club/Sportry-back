package com.gamza.sportry.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MainPostsResponseDto {
    private Long id;
    private String title;
    private String sport;
    private int likeCount;
}
