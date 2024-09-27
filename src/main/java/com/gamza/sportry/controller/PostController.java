package com.gamza.sportry.controller;

import com.gamza.sportry.dto.post.CrewPostsResponseDto;
import com.gamza.sportry.dto.post.PostRequestDto;
import com.gamza.sportry.dto.post.MainPostsResponseDto;
import com.gamza.sportry.dto.post.PostResponseDto;
import com.gamza.sportry.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Tag(name = "Post Controller", description = "게시글 API")
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 작성")
    @PostMapping()
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postRequestDto,  HttpServletRequest request) {
        postService.createPost(postRequestDto, request);
        return ResponseEntity.ok("게시글 작성 완료");
    }

    @Operation(summary = "게시글 목록 조회 (메인페이지)")
    @GetMapping("/main")
    public ResponseEntity<List<MainPostsResponseDto>> findMainPosts() {
        List<MainPostsResponseDto> posts = postService.findMainPosts();
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "게시글 목록 조회 (크루페이지)")
    @GetMapping("/crew")
    public ResponseEntity<List<CrewPostsResponseDto>> findCrewPosts() {
        List<CrewPostsResponseDto> posts = postService.findCrewPosts();
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "게시글 조회")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findPost(@PathVariable Long id) {
        PostResponseDto post = postService.findPost(id);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        postService.updatePost(id, postRequestDto, request);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, HttpServletRequest request) {
        postService.deletePost(id, request);
        return ResponseEntity.ok("게시글 삭제 완료");
    }

}
