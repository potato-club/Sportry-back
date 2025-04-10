package com.gamza.sportry.service;

import com.gamza.sportry.core.error.ErrorCode;
import com.gamza.sportry.core.error.exception.NotFoundException;
import com.gamza.sportry.core.security.JwtTokenProvider;
import com.gamza.sportry.dto.user.ResetPasswordRequestDto;
import com.gamza.sportry.dto.user.RetrievePasswordRequestDto;
import com.gamza.sportry.dto.user.RetrieveUserIdRequestDto;
import com.gamza.sportry.entity.UserEntity;
import com.gamza.sportry.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserEntity findUserByToken(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        return token == null ? null : userRepo.findByUserId(jwtTokenProvider.getUserId(token)).orElse(null);
    }


    @Transactional(readOnly = true)
    public String retrieveUserId (RetrieveUserIdRequestDto requestDto) {

        String nickName = requestDto.getNickName();
        String userEmail = requestDto.getUserEmail();
        UserEntity user = userRepo.findByNickNameAndEmail(nickName, userEmail)
                .orElseThrow(() -> new NotFoundException("닉네임과 이메일이 일치하는 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_EXCEPTION));

        return user.getUserId();

    }

    @Transactional(readOnly = true)
    public Long findUserPkForPasswordReset(RetrievePasswordRequestDto requestDto) {
        UserEntity user = userRepo.findByUserIdAndNickNameAndEmail(
                requestDto.getUserId(),
                requestDto.getNickName(),
                requestDto.getUserEmail()
        ).orElseThrow(() -> new NotFoundException("일치하는 사용자를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_EXCEPTION));

        return user.getId(); // PK 반환
    }

    @Transactional
    public void resetPassword(ResetPasswordRequestDto requestDto) {
        UserEntity user = userRepo.findById(requestDto.getId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_EXCEPTION));

        String encodedPassword = passwordEncoder.encode(requestDto.getNewPassword());
        user.updatePassword(encodedPassword);
    }

}
