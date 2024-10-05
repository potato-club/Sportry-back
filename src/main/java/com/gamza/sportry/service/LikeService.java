package com.gamza.sportry.service;

import com.gamza.sportry.core.error.ErrorCode;
import com.gamza.sportry.core.error.exception.NotFoundException;
import com.gamza.sportry.entity.*;
import com.gamza.sportry.repo.CommentLikeRepo;
import com.gamza.sportry.repo.CommentRepo;
import com.gamza.sportry.repo.PostLikeRepo;
import com.gamza.sportry.repo.PostRepo;
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
    private final PostRepo postRepo;
    private final CommentRepo commentRepo;
    private final UserService userService;

    public String likePost(Long id, HttpServletRequest request) {
        UserEntity user = userService.findUserByToken(request);
        if (user == null)
            throw new NotFoundException("로그인 후 좋아요 활동이 가능합니다", ErrorCode.NOT_FOUND_EXCEPTION);

        PostEntity post = postRepo.findById(id).orElse(null);
        if (post == null)
            throw new NotFoundException("찾을 수 없는 게시글입니다", ErrorCode.NOT_FOUND_EXCEPTION);

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
        UserEntity user = userService.findUserByToken(request);
        if (user == null)
            throw new NotFoundException("로그인 후 좋아요 활동이 가능합니다", ErrorCode.NOT_FOUND_EXCEPTION);

        CommentEntity comment = commentRepo.findById(id).orElse(null);
        if (comment == null)
            throw new NotFoundException("찾을 수 없는 댓글입니다", ErrorCode.NOT_FOUND_EXCEPTION);

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
