package com.gamza.sportry.controller;

import com.gamza.sportry.dto.myPage.MyPageRequestDto;
import com.gamza.sportry.dto.myPage.UserInfoResponseDto;
import com.gamza.sportry.dto.myPage.UpdateUserInfoRequestDto;
import com.gamza.sportry.service.MypageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Tag(name = "MyPage Controller", description = "마이페이지 API")
public class MyPageController {

    private final MypageService mypageService;

    @Operation(summary = "유저 정보 확인")
    @GetMapping("")
    public UserInfoResponseDto showUserInfo(HttpServletRequest request) {
        return mypageService.showUserInfo(request);
    }

//    @Operation(summary = "유저 정보 수정")
//    @PutMapping("/info")
//    public ResponseEntity<String> updateUserInfo(@RequestBody UpdateUserInfoRequestDto requestDto, HttpServletRequest request) {
//        mypageService.updateUserInfo(requestDto, request);
//        return ResponseEntity.ok("유저 정보 수정 완료");
//    }
//
//    @Operation(summary = "마이페이지 - 유저 프로필 수정")
//    @PostMapping("/myPage/info")
//    public ResponseEntity<String> showUserInfo(@RequestBody MyPageRequestDto myPageRequestDto, HttpServletRequest request) {
//        String userId = mypageService.updateUserImg(myPageRequestDto, request);
//        return ResponseEntity.ok(userId);
//    }
}
