package com.gamza.sportry.validation;

import com.gamza.sportry.core.error.ErrorCode;
import com.gamza.sportry.core.error.exception.NotFoundException;
import com.gamza.sportry.entity.PostEntity;
import com.gamza.sportry.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidation {

    private final PostRepo postRepo;

    public PostEntity isPresentPost(Long post_id) {
        PostEntity post = postRepo.findById(post_id).orElse(null);
        if (post == null)
            throw new NotFoundException("찾을 수 없는 게시글입니다", ErrorCode.NOT_FOUND_EXCEPTION);
        return post;
    }

}
