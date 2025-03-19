package com.gamza.sportry.service;

import com.gamza.sportry.core.error.ErrorCode;
import com.gamza.sportry.core.error.exception.BadRequestException;
import com.gamza.sportry.core.error.exception.InvalidTokenException;
import com.gamza.sportry.core.security.JwtTokenProvider;
import com.gamza.sportry.dto.myPage.MyPageRequestDto;
import com.gamza.sportry.dto.myPage.UpdateUserInfoRequestDto;
import com.gamza.sportry.dto.myPage.UserInfoResponseDto;
import com.gamza.sportry.entity.FileEntity;
import com.gamza.sportry.entity.UserEntity;
import com.gamza.sportry.repo.UserRepo;
import com.gamza.sportry.repo.file.FileRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MypageService {
    private final UserRepo userRepo;
    private final FileRepo fileRepo;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public UserInfoResponseDto showUserInfo(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);


        if (token == null || !jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.", ErrorCode.BAD_REQUEST_EXCEPTION);
        }

        String userId = jwtTokenProvider.getUserId(token);
        UserEntity user = userRepo.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("사용자를 찾을 수 없습니다.", ErrorCode.BAD_REQUEST_EXCEPTION));

        return new UserInfoResponseDto(
                user.getUserId(),
                user.getProfileImg() != null ? user.getProfileImg().getFileUrl() : null,
                user.getBirth(),
                user.getGender(),
                user.getNickName(),
                user.getEmail()
        );
    }

//    @Transactional
//    public void updateUserInfo(UpdateUserInfoRequestDto requestDto, HttpServletRequest request) {
//        String token = jwtTokenProvider.resolveAccessToken(request);
//
//        if (token == null || !jwtTokenProvider.validateToken(token)) {
//            throw new InvalidTokenException("유효하지 않은 토큰입니다.", ErrorCode.BAD_REQUEST_EXCEPTION);
//        }
//
//        String userId = jwtTokenProvider.getUserId(token);
//        UserEntity user = userRepo.findByUserId(userId)
//                .orElseThrow(() -> new BadRequestException("사용자를 찾을 수 없습니다.", ErrorCode.BAD_REQUEST_EXCEPTION));
//
//        user.updateUserInfo(requestDto);
//        userRepo.save(user);
//    }

//    @Transactional
//    public String updateUserImg(MyPageRequestDto myPageRequestDto, HttpServletRequest request) {
//        String token = jwtTokenProvider.resolveAccessToken(request);
//
//        if (token == null || !jwtTokenProvider.validateToken(token)) {
//            throw new InvalidTokenException("유효하지 않은 토큰입니다.", ErrorCode.BAD_REQUEST_EXCEPTION);
//        }
//
//        String userId = jwtTokenProvider.getUserId(token);
//        UserEntity user = userRepo.findByUserId(userId)
//                .orElseThrow(() -> new BadRequestException("사용자를 찾을 수 없습니다.", ErrorCode.BAD_REQUEST_EXCEPTION));
//
//        FileEntity profileImg = new FileEntity(
//                myPageRequestDto.getNickName() + "_profile",
//                myPageRequestDto.getProfileUrl(),
//                null,
//                null,
//                user
//        );
//
//        fileRepo.save(profileImg);
//        user.updateProfileImg(profileImg);
//        userRepo.save(user);
//
//        return user.getUserId();
//    }


}
