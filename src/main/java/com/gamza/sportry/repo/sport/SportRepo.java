package com.gamza.sportry.repo.sport;

import com.gamza.sportry.entity.SportEntity;
import com.gamza.sportry.entity.SportTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportRepo extends JpaRepository<SportEntity, Long> {
    List<SportEntity> findByType(SportTypeEntity type);
    SportEntity findByName(String name);
}
