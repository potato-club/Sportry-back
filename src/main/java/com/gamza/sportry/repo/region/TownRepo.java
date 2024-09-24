package com.gamza.sportry.repo.region;

import com.gamza.sportry.entity.TownEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepo extends JpaRepository<TownEntity, Long> {
}
