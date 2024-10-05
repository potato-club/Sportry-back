package com.gamza.sportry.repo;

import com.gamza.sportry.entity.PostLikeEntity;
import com.gamza.sportry.entity.PostEntity;
import com.gamza.sportry.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepo extends JpaRepository<PostLikeEntity, Long> {
    PostLikeEntity findByUserAndPost(UserEntity user, PostEntity post);
}
