package com.gamza.sportry.service;

import com.gamza.sportry.core.error.ErrorCode;
import com.gamza.sportry.core.error.exception.NotFoundException;
import com.gamza.sportry.core.error.exception.UnAuthorizedException;
import com.gamza.sportry.dto.comment.CommentRequestDto;
import com.gamza.sportry.dto.comment.CommentResponseDto;
import com.gamza.sportry.entity.CommentEntity;
import com.gamza.sportry.entity.PostEntity;
import com.gamza.sportry.entity.UserEntity;
import com.gamza.sportry.repo.CommentRepo;
import com.gamza.sportry.repo.PostRepo;
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
    private final PostRepo postRepo;
    private final UserService userService;

    public void createComment(Long post_id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        UserEntity user = userService.findUserByToken(request);
        if (user == null)
            throw new NotFoundException("로그인 후 게시글 작성이 가능합니다", ErrorCode.NOT_FOUND_EXCEPTION);

        PostEntity post = postRepo.findById(post_id).orElse(null);
        if (post == null)
            throw new NotFoundException("찾을 수 없는 게시글입니다", ErrorCode.NOT_FOUND_EXCEPTION);

        CommentEntity comment = CommentEntity.builder()
                .user(user)
                .post(post)
                .content(commentRequestDto.getContent())
                .likeCount(0)
                .commentCount(0)
                .build();

        post.upCommentCount();
        commentRepo.save(comment);
    }

    public List<CommentResponseDto> findComments() {
        List<CommentEntity> comments = commentRepo.findAll();
        return comments.stream()
                .map(comment -> CommentResponseDto.builder()
                        .id(comment.getId())
                        .createdDate(comment.getCreatedDate())
                        .content(comment.getContent())
                        .likeCount(comment.getLikeCount())
                        .commentCount(comment.getCommentCount())
                        .build())
                .collect(Collectors.toList());
    }

    public void updateComment(Long comment_id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        UserEntity user = userService.findUserByToken(request);
        if (user == null)
            throw new UnAuthorizedException("댓글 수정 권한이 없습니다", ErrorCode.UNAUTHORIZED_EXCEPTION);

        CommentEntity comment = commentRepo.findById(comment_id).orElse(null);
        if (comment == null)
            throw new NotFoundException("수정할 수 없는 댓글입니다", ErrorCode.NOT_FOUND_EXCEPTION);
        if (comment.getUser() != user)
            throw new UnAuthorizedException("댓글 수정 권한이 없습니다", ErrorCode.UNAUTHORIZED_EXCEPTION);

        comment.update(commentRequestDto);
    }

    public void deleteComment(Long comment_id, HttpServletRequest request) {
        UserEntity user = userService.findUserByToken(request);
        if (user == null)
            throw new UnAuthorizedException("댓글 삭제 권한이 없습니다", ErrorCode.UNAUTHORIZED_EXCEPTION);

        CommentEntity comment = commentRepo.findById(comment_id).orElse(null);
        if (comment == null)
            throw new NotFoundException("삭제할 수 없는 댓글입니다", ErrorCode.NOT_FOUND_EXCEPTION);
        if (comment.getUser() != user)
            throw new UnAuthorizedException("댓글 삭제 권한이 없습니다", ErrorCode.UNAUTHORIZED_EXCEPTION);

        PostEntity post = comment.getPost();
        post.downCommentCount();
        commentRepo.delete(comment);
    }

}
