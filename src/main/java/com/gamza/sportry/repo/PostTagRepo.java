package com.gamza.sportry.repo;

import com.gamza.sportry.entity.PostEntity;
import com.gamza.sportry.entity.PostTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepo extends JpaRepository<PostTagEntity, Long> {
    void deleteAllByPost(PostEntity post);
}
