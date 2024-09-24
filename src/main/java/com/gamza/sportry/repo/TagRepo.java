package com.gamza.sportry.repo;

import com.gamza.sportry.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends JpaRepository<TagEntity, Long> {
    TagEntity findByName(String name);
}
