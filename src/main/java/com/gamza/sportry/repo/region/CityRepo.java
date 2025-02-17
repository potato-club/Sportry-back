package com.gamza.sportry.repo.region;

import com.gamza.sportry.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends JpaRepository<CityEntity, Long> {
}
