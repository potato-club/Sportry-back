package com.gamza.sportry.dto.main.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MainUrgentPostResponseDto {
    private final long id;
    private final String title;
    private final List<String> tags;
}
