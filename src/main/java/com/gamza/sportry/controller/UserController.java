package com.gamza.sportry.controller;

import com.gamza.sportry.dto.user.*;
import com.gamza.sportry.service.UserService;
import com.gamza.sportry.service.login.EmailVerificationService;
import com.gamza.sportry.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final LoginService loginService;
    private final UserService userService;
    private final EmailVerificationService emailVerificationService;

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

    @PostMapping("/password/verify")
    public ResponseEntity<Long> verifyUserForPasswordReset(@RequestBody RetrievePasswordRequestDto request) {
        Long userPk = userService.findUserPkForPasswordReset(request);
        return ResponseEntity.ok(userPk);
    }

    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDto request) {
        userService.resetPassword(request);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    @Operation(summary = "이메일 인증 코드 발송")
    @PostMapping("/register/send-email")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody EmailVerificationRequestDto requestDto) {
        emailVerificationService.sendVerificationEmail(requestDto.getEmail());
        return ResponseEntity.ok("이메일 인증 코드 전송 완료");
    }

    @Operation(summary = "이메일 인증 코드 검증")
    @PostMapping("/register/verify-code")
    public ResponseEntity<String> verifyEmailCode(@RequestBody EmailVerificationCheckDto requestDto) {
        boolean isVerified = emailVerificationService.verifyCode(requestDto.getEmail(), requestDto.getCode());
        if (isVerified) {
            return ResponseEntity.ok("이메일 인증 성공!");
        } else {
            return ResponseEntity.badRequest().body("인증 코드가 일치하지 않습니다.");
        }
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/user/withdraw")
    public ResponseEntity<String> withdrawUser(HttpServletRequest request) {
        userService.withdrawUser(request);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

}
