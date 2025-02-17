package com.gamza.sportry.repo.sport;

import com.gamza.sportry.entity.SportTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportTypeRepo extends JpaRepository<SportTypeEntity, Long> {
}
