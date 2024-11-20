package com.gamza.sportry.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CrewPostsResponseDto {

    private Long id;

    private String postDate;
    private String title;
    private String postState;
    private String sport;
    private int viewCount;
    private int likeCount;
    private int commentCount;

    private List<String> tag;
}
