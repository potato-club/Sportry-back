package com.gamza.sportry.dto.post;

import com.gamza.sportry.entity.custom.PostState;
import lombok.Data;

import java.util.List;

@Data
public class PostRequestDto {
    private String title;
    private String content;
    private PostState postState;
    private List<String> tag;
}
