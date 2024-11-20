package com.gamza.sportry.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CommentResponseDto {

    private Long parent_id;
    private Long id;

    private String commentDate;
    private String content;
    private int likeCount;

    private List<CommentResponseDto> children = new ArrayList<>();

}
