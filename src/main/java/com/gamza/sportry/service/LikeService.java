package com.gamza.sportry.service;

import com.gamza.sportry.entity.*;
import com.gamza.sportry.repo.CommentLikeRepo;
import com.gamza.sportry.repo.PostLikeRepo;
import com.gamza.sportry.validation.CommentValidation;
import com.gamza.sportry.validation.PostValidation;
import com.gamza.sportry.validation.UserValidation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeRepo postLikeRepo;
    private final CommentLikeRepo commentLikeRepo;

    private final UserValidation userValidation;
    private final PostValidation postValidation;
    private final CommentValidation commentValidation;

    public String likePost(Long id, HttpServletRequest request) {
        UserEntity user = userValidation.isPresentUser(request);
        PostEntity post = postValidation.isPresentPost(id);

        PostLikeEntity postLike = postLikeRepo.findByUserAndPost(user, post);
        if (postLike == null) {
            postLike = PostLikeEntity.builder()
                    .user(user)
                    .post(post)
                    .build();
            postLikeRepo.save(postLike);
            post.upPostLikeCount();
            return "해당 게시글에 좋아요가 추가되었습니다";
        }

        postLikeRepo.delete(postLike);
        post.downPostLikeCount();
        return "해당 게시글에 좋아요가 취소되었습니다";
    }

    public String likeComment(Long id, HttpServletRequest request) {
        UserEntity user = userValidation.isPresentUser(request);
        CommentEntity comment = commentValidation.isPresentComment(id);

        CommentLikeEntity commentLike = commentLikeRepo.findByUserAndComment(user, comment);
        if (commentLike == null) {
            commentLike = CommentLikeEntity.builder()
                    .user(user)
                    .comment(comment)
                    .build();
            commentLikeRepo.save(commentLike);
            comment.upCommentLikeCount();
            return "해당 댓글에 좋아요가 추가되었습니다";
        }

        commentLikeRepo.delete(commentLike);
        comment.downCommentLikeCount();
        return "해당 댓글에 좋아요가 취소되었습니다";
    }

}
