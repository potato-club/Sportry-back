package com.gamza.sportry.controller;

import com.gamza.sportry.service.LikeService;
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
public class LikeController {

    private final LikeService likeService;

    @PostMapping("post/{id}")
    public ResponseEntity<String> likePost(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity.ok(likeService.likePost(id, request));
    }

}
