package com.gamza.sportry.repo;

import com.gamza.sportry.entity.PostEntity;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findTop10ByOrderByViewCountDescLikeCountDesc();
    List<PostEntity> findAllByOrderByIdDesc(PageRequest pageRequest);
    List<PostEntity> findByIdLessThanOrderByIdDesc(Long lastPostId, PageRequest pageRequest);

}
