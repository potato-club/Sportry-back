package com.gamza.sportry.validation;

import com.gamza.sportry.core.error.ErrorCode;
import com.gamza.sportry.core.error.exception.NotFoundException;
import com.gamza.sportry.core.error.exception.UnAuthorizedException;
import com.gamza.sportry.entity.CommentEntity;
import com.gamza.sportry.entity.UserEntity;
import com.gamza.sportry.repo.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidation {

    private final CommentRepo commentRepo;

    public CommentEntity isPresentComment(Long comment_id) {
        CommentEntity comment = commentRepo.findById(comment_id).orElse(null);
        if (comment == null)
            throw new NotFoundException("찾을 수 없는 댓글입니다", ErrorCode.NOT_FOUND_EXCEPTION);
        return comment;
    }

    public void isParent(CommentEntity comment) {
        if (comment.getParent() != null)
            throw new NotFoundException("대댓글을 작성할 수 없습니다", ErrorCode.NOT_FOUND_EXCEPTION);
    }

    public void isValidateComment(UserEntity user, CommentEntity comment) {
        if (comment.getUser() != user)
            throw new UnAuthorizedException("댓글을 변경할 수 없습니다", ErrorCode.UNAUTHORIZED_EXCEPTION);
    }


}
