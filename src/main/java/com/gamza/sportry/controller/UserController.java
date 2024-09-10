package com.gamza.sportry.controller;

import com.gamza.sportry.dto.user.LoginRequestDto;
import com.gamza.sportry.dto.user.RegisterRequestDto;
import com.gamza.sportry.service.login.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest, HttpServletResponse response) {
        loginService.normalLogin(loginRequest, response);
        return ResponseEntity.ok("로그인 성공, 헤더에 토큰 확인");
    }
    @PostMapping("/register")
    public ResponseEntity<String> registering(@RequestBody RegisterRequestDto registerRequest, HttpServletResponse response) {
        loginService.register(registerRequest, response);
        return ResponseEntity.ok("가입 성공, 헤더에 토큰 확인");
    }

}