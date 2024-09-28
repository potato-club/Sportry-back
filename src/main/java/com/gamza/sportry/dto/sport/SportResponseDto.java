package com.gamza.sportry.dto.sport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SportResponseDto {
    private Long id;
    private String name;
}
