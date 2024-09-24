package com.gamza.sportry.controller;

import com.gamza.sportry.dto.post.PostRequestDto;
import com.gamza.sportry.dto.post.PostListResponseDto;
import com.gamza.sportry.dto.post.PostResponseDto;
import com.gamza.sportry.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping()
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postRequestDto,  HttpServletRequest request) {
        postService.createPost(postRequestDto, request);
        return ResponseEntity.ok("게시글 작성 완료");
    }

    @GetMapping()
    public ResponseEntity<List<PostListResponseDto>> findPostList() {
        List<PostListResponseDto> posts = postService.findPostList();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findPost(@PathVariable Long id) {
        PostResponseDto post = postService.findPost(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        postService.updatePost(id, postRequestDto, request);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, HttpServletRequest request) {
        postService.deletePost(id, request);
        return ResponseEntity.ok("게시글 삭제 완료");
    }

}
