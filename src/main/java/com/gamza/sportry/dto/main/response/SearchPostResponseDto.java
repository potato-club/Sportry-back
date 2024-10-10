package com.gamza.sportry.dto.main.response;

import com.gamza.sportry.entity.PostEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchPostResponseDto {

    private Long id;
    private String title;
    private String content;
    private List<String> tags;

    public SearchPostResponseDto(PostEntity post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.tags = post.getPostTags().stream()
                .map(postTag -> postTag.getTag().getName())
                .collect(Collectors.toList());
    }


}
