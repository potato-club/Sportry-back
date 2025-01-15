package com.gamza.sportry.controller;

import com.gamza.sportry.dto.user.LoginRequestDto;
import com.gamza.sportry.dto.user.RegisterRequestDto;
import com.gamza.sportry.dto.user.RetrieveUserIdRequestDto;
import com.gamza.sportry.service.UserService;
import com.gamza.sportry.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final UserService userService;

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest, HttpServletResponse response) {
        loginService.normalLogin(loginRequest, response);
        return ResponseEntity.ok("로그인 성공, 헤더에 토큰 확인");
    }

    @Operation(summary = "회원가입")
    @PostMapping("/register")
    public ResponseEntity<String> registering(@RequestBody RegisterRequestDto registerRequest, HttpServletResponse response) {
        loginService.register(registerRequest, response);
        return ResponseEntity.ok("가입 성공, 헤더에 토큰 확인");
    }

    @Operation(summary = "아이디 찾기")
    @PostMapping("/id/retrieve")
    public ResponseEntity<String> retrieveUserId(@RequestBody RetrieveUserIdRequestDto retrieveRequest) {
        String userId = userService.retrieveUserId(retrieveRequest);
        return ResponseEntity.ok(userId);
    }
}