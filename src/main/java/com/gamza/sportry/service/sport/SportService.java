package com.gamza.sportry.service.sport;

import com.gamza.sportry.dto.sport.SportResponseDto;
import com.gamza.sportry.entity.SportEntity;
import com.gamza.sportry.entity.SportTypeEntity;
import com.gamza.sportry.repo.sport.SportRepo;
import com.gamza.sportry.repo.sport.SportTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SportService {

    private final SportTypeRepo sportTypeRepo;
    private final SportRepo sportRepo;

    public List<SportResponseDto> findTypeList() {
        List<SportTypeEntity> sportTypes = sportTypeRepo.findAll();
        return sportTypes.stream()
                .map(sportType -> SportResponseDto.builder()
                        .id(sportType.getId())
                        .name(sportType.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<SportResponseDto> findSportList(Long typeId) {
        List<SportEntity> sports = sportRepo.findByType(sportTypeRepo.findById(typeId).orElse(null));
        return sports.stream()
                .map(sport -> SportResponseDto.builder()
                        .id(sport.getId())
                        .name(sport.getName())
                        .build())
                .collect(Collectors.toList());
    }

}
