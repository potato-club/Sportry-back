package com.gamza.sportry.service.region;

import com.gamza.sportry.core.error.ErrorCode;
import com.gamza.sportry.core.error.exception.BadRequestException;
import com.gamza.sportry.core.security.JwtTokenProvider;
import com.gamza.sportry.dto.region.CityResponseDto;
import com.gamza.sportry.dto.region.RegionResponseDto;
import com.gamza.sportry.dto.region.SelectRegionRequestDto;
import com.gamza.sportry.dto.region.TownResponseDto;
import com.gamza.sportry.entity.CityEntity;
import com.gamza.sportry.entity.RegionEntity;
import com.gamza.sportry.entity.TownEntity;
import com.gamza.sportry.entity.UserEntity;
import com.gamza.sportry.repo.region.CityRepo;
import com.gamza.sportry.repo.region.RegionRepo;
import com.gamza.sportry.repo.UserRepo;
import com.gamza.sportry.repo.region.TownRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegionService {


    private final RegionRepo regionRepository;
    private final CityRepo cityRepository;
    private final TownRepo townRepository;
    private final UserRepo userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    // 시도 목록 가져오기
    public List<RegionResponseDto> getAllRegion() {
        List<RegionEntity> regions = regionRepository.findAll();
        return regions.stream().map(region -> new RegionResponseDto(region.getId(), region.getName())).collect(Collectors.toList());
    }

    // 시군구 목록 가져오기
    public List<CityResponseDto> getCitiesByRegion(Long regionId) {
        RegionEntity region = regionRepository.findById(regionId).orElseThrow(() -> new IllegalArgumentException("해당 시도가 없습니다."));
        List<CityEntity> cities = region.getCities();
        return cities.stream().map(city -> new CityResponseDto(city.getId(), city.getName())).collect(Collectors.toList());
    }

    // 읍면동 목록 가져오기
    public List<TownResponseDto> getTownsByCity(Long cityId) {
        CityEntity city = cityRepository.findById(cityId).orElseThrow(() -> new IllegalArgumentException("해당 시군구가 없습니다."));
        List<TownEntity> towns = city.getTowns();
        return towns.stream().map(town -> new TownResponseDto(town.getId(), town.getName())).collect(Collectors.toList());
    }

    // 사용자가 선택한 지역 저장
    @Transactional
    public void saveUserRegion(SelectRegionRequestDto selectRegionDto, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);

        if (token == null || !jwtTokenProvider.validateToken(token)) {
            throw new BadRequestException("유효하지 않은 토큰입니다.", ErrorCode.BAD_REQUEST_EXCEPTION);
        }
        if (selectRegionDto.getTownIds() == null || selectRegionDto.getTownIds().isEmpty()) {
            throw new BadRequestException("선택된 지역이 없습니다.", ErrorCode.BAD_REQUEST_EXCEPTION);
        }

        String userId = jwtTokenProvider.getUserId(token);

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        for (Long townId : selectRegionDto.getTownIds()) {
            TownEntity town = townRepository.findById(townId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 읍면동을 찾을 수 없습니다."));

            // 중복 선택 방지 로직 추가
            if (user.getTown() != null && user.getTown().getId().equals(town.getId())) {
                continue; // 이미 선택한 지역이면 넘어감
            }

            user.setTown(town);
        }

        userRepository.save(user);
    }

}
