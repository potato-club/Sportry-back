package com.gamza.sportry.dto.post;

import com.gamza.sportry.entity.custom.PostState;
import lombok.Data;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostRequestDto {
    private String title;
    private String content;
    private PostState postState;
    private String sport;
    private List<String> tag;
    private List<MultipartFile> images;
}
