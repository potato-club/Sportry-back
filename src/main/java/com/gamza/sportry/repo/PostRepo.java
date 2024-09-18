package com.gamza.sportry.repo;

import com.gamza.sportry.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<PostEntity, Long> {
}
