package com.gamza.sportry.dto.region;

import lombok.Data;

import java.util.List;

@Data
public class SelectRegionRequestDto {
    private List<Long> townIds;
}
