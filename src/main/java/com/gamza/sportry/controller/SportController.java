package com.gamza.sportry.controller;

import com.gamza.sportry.dto.sport.SportResponseDto;
import com.gamza.sportry.entity.SportTypeEntity;
import com.gamza.sportry.service.sport.SportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sport")
@Tag(name = "sport Controller", description = "운동 API")
public class SportController {

    private final SportService sportService;

    @Operation(summary = "운동 종류 조회")
    @GetMapping()
    public ResponseEntity<List<SportResponseDto>> findTypeList() {
        List<SportResponseDto> sportTypes = sportService.findTypeList();
        return ResponseEntity.ok(sportTypes);
    }

    @Operation(summary = "운동 조회")
    @GetMapping("/{id}")
    public ResponseEntity<List<SportResponseDto>> findSportList(@PathVariable Long id) {
        List<SportResponseDto> sports = sportService.findSportList(id);
        return ResponseEntity.ok(sports);
    }

}
