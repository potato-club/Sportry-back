package com.gamza.sportry.controller;

import com.gamza.sportry.dto.comment.CommentRequestDto;
import com.gamza.sportry.dto.comment.CommentResponseDto;
import com.gamza.sportry.dto.post.MainPostsResponseDto;
import com.gamza.sportry.dto.post.PostRequestDto;
import com.gamza.sportry.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
@Tag(name = "Comment Controller", description = "댓글 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성")
    @PostMapping("/{post_id}")
    public ResponseEntity<String> createComment(@PathVariable Long post_id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        commentService.createComment(post_id, commentRequestDto, request);
        return ResponseEntity.ok("댓글 작성 완료");
    }

    @Operation(summary = "댓글 조회")
    @GetMapping()
    public ResponseEntity<List<CommentResponseDto>> findComments() {
        List<CommentResponseDto> comments = commentService.findComments();
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{comment_id}")
    public ResponseEntity<String> updateComment(@PathVariable Long comment_id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        commentService.updateComment(comment_id, commentRequestDto, request);
        return ResponseEntity.ok("댓글 수정 완료");
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{comment_id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long comment_id, HttpServletRequest request) {
        commentService.deleteComment(comment_id, request);
        return ResponseEntity.ok("댓글 삭제 완료");
    }

}
