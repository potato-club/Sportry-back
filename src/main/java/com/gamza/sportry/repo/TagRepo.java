package com.gamza.sportry.repo;

import com.gamza.sportry.entity.TagEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends JpaRepository<TagEntity, Long> {
    TagEntity findByName(String name);

    @Query("SELECT t FROM TagEntity t ORDER BY t.searchCount DESC")
    List<TagEntity> findTop10BySearchCount();
}
