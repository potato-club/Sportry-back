package com.gamza.sportry.repo.region;

import com.gamza.sportry.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepo extends JpaRepository<RegionEntity, Long> {
}
