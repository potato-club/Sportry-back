package com.gamza.sportry.repo;

import com.gamza.sportry.entity.LikeEntity;
import com.gamza.sportry.entity.PostEntity;
import com.gamza.sportry.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepo extends JpaRepository<LikeEntity, Long> {
    LikeEntity findByUserAndPost(UserEntity user, PostEntity post);
}
