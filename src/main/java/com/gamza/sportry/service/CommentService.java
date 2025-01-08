package com.gamza.sportry.service;

import com.gamza.sportry.dto.comment.CommentRequestDto;
import com.gamza.sportry.dto.comment.CommentResponseDto;
import com.gamza.sportry.entity.CommentEntity;
import com.gamza.sportry.entity.PostEntity;
import com.gamza.sportry.entity.UserEntity;
import com.gamza.sportry.repo.CommentRepo;
import com.gamza.sportry.validation.CommentValidation;
import com.gamza.sportry.validation.PostValidation;
import com.gamza.sportry.validation.UserValidation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;
    private final TimeService timeService;

    private final UserValidation userValidation;
    private final PostValidation postValidation;
    private final CommentValidation commentValidation;

    public void createComment(Long post_id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        UserEntity user = userValidation.isPresentUser(request);
        PostEntity post = postValidation.isPresentPost(post_id);

        CommentEntity comment = CommentEntity.builder()
                .user(user)
                .post(post)
                .city(user.getCity())
                .content(commentRequestDto.getContent())
                .likeCount(0)
                .build();

        post.upCommentCount();
        commentRepo.save(comment);
    }

    public void createReply(Long post_id, Long comment_id,
                            CommentRequestDto commentRequestDto, HttpServletRequest request) {
        UserEntity user = userValidation.isPresentUser(request);
        PostEntity post = postValidation.isPresentPost(post_id);
        CommentEntity parent = commentValidation.isPresentComment(comment_id);
        commentValidation.isParent(parent);

        CommentEntity comment = CommentEntity.builder()
                .user(user)
                .post(post)
                .city(user.getCity())
                .parent(parent)
                .content(commentRequestDto.getContent())
                .likeCount(0)
                .build();

        post.upCommentCount();
        commentRepo.save(comment);
    }

    public List<CommentResponseDto> findComments(Long post_id) {
        PostEntity post = postValidation.isPresentPost(post_id);

        List<CommentEntity> comments = post.getComments();
        return comments.stream()
                .filter(comment -> comment.getParent() == null)
                .map(comment -> CommentResponseDto.builder()
                        .id(comment.getId())
                        .nickName(comment.getUser().getNickName())
                        .region(comment.getCity().getName())
                        .commentDate(timeService.timeSet(comment.getCreatedDate()))
                        .content(comment.getContent())
                        .likeCount(comment.getLikeCount())
                        .children(findreplies(comment.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<CommentResponseDto> findreplies(Long comment_id) {
        CommentEntity parent = commentValidation.isPresentComment(comment_id);

        List<CommentEntity> replies = parent.getChildren();
        return replies.stream()
                .map(reply -> CommentResponseDto.builder()
                        .parent_id(reply.getParent().getId())
                        .id(reply.getId())
                        .nickName(reply.getUser().getNickName())
                        .region(reply.getCity().getName())
                        .commentDate(timeService.timeSet(reply.getCreatedDate()))
                        .content(reply.getContent())
                        .likeCount(reply.getLikeCount())
                        .build())
                .collect(Collectors.toList());
    }

    public void updateComment(Long comment_id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        UserEntity user = userValidation.isPresentUser(request);
        CommentEntity comment = commentValidation.isPresentComment(comment_id);
        commentValidation.isValidateComment(user, comment);

        comment.update(commentRequestDto);
    }

    public void deleteComment(Long comment_id, HttpServletRequest request) {
        UserEntity user = userValidation.isPresentUser(request);
        CommentEntity comment = commentValidation.isPresentComment(comment_id);
        commentValidation.isValidateComment(user, comment);

        PostEntity post = comment.getPost();
        post.downCommentCount();
        commentRepo.delete(comment);
    }

}
