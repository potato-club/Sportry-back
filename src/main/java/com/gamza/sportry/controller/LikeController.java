package com.gamza.sportry.controller;

import com.gamza.sportry.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/like")
@Tag(name = "Like Controller", description = "좋아요 API")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "게시글 좋아요")
    @PostMapping("post/{id}")
    public ResponseEntity<String> likePost(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity.ok(likeService.likePost(id, request));
    }

    @Operation(summary = "댓글 좋아요")
    @PostMapping("comment/{id}")
    public ResponseEntity<String> likeComment(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity.ok(likeService.likeComment(id, request));
    }

}
