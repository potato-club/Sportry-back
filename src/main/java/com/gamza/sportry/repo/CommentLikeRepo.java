package com.gamza.sportry.repo;

import com.gamza.sportry.entity.CommentEntity;
import com.gamza.sportry.entity.CommentLikeEntity;
import com.gamza.sportry.entity.PostEntity;
import com.gamza.sportry.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepo extends JpaRepository<CommentLikeEntity, Long> {
    CommentLikeEntity findByUserAndComment(UserEntity user, CommentEntity comment);
}
