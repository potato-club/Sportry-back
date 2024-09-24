package com.gamza.sportry.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamza.sportry.entity.custom.PostState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PostResponseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDateTime createdDate;

    private String title;
    private String content;
    private String postState;
    private int viewCount;
    private int likeCount;
    private int commentCount;

    private List<String> tag;
}
