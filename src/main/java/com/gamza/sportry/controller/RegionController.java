package com.gamza.sportry.controller;

import com.gamza.sportry.dto.region.CityResponseDto;
import com.gamza.sportry.dto.region.RegionResponseDto;
import com.gamza.sportry.dto.region.SelectRegionRequestDto;
import com.gamza.sportry.dto.region.TownResponseDto;
import com.gamza.sportry.service.region.RegionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

    // 시도 목록 불러오기
    @GetMapping("/")
    public ResponseEntity<List<RegionResponseDto>> getRegion() {
        List<RegionResponseDto> provinces = regionService.getAllRegion();
        return ResponseEntity.ok(provinces);
    }
    // 시군구 목록 불러오기
    @GetMapping("/cities/{regionId}")
    public ResponseEntity<List<CityResponseDto>> getCities(@PathVariable Long regionId) {
        List<CityResponseDto> cities = regionService.getCitiesByRegion(regionId);
        return ResponseEntity.ok(cities);
    }

    // 읍면동 목록 불러오기
    @GetMapping("/towns/{cityId}")
    public ResponseEntity<List<TownResponseDto>> getTowns(@PathVariable Long cityId) {
        List<TownResponseDto> towns = regionService.getTownsByCity(cityId);
        return ResponseEntity.ok(towns);
    }

    // 선택한 지역 저장
    @PostMapping("/select")
    public ResponseEntity<String> saveUserRegion(@RequestBody SelectRegionRequestDto selectRegionRequestDto, HttpServletRequest request) {
        regionService.saveUserRegion(selectRegionRequestDto, request);
        return ResponseEntity.ok("지역 설정이 완료되었습니다.");
    }
}