package com.gamza.sportry.service.login;

import com.gamza.sportry.core.error.ErrorCode;
import com.gamza.sportry.core.error.exception.BadRequestException;
import com.gamza.sportry.core.error.exception.DuplicateException;
import com.gamza.sportry.core.error.exception.NotFoundException;
import com.gamza.sportry.core.error.exception.UnAuthorizedException;
import com.gamza.sportry.core.security.JwtTokenProvider;
import com.gamza.sportry.dto.user.LoginRequestDto;
import com.gamza.sportry.dto.user.RegisterRequestDto;
import com.gamza.sportry.entity.UserEntity;
import com.gamza.sportry.entity.custom.UserRole;
import com.gamza.sportry.repo.UserRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final UserRepo userRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void normalLogin(LoginRequestDto loginRequest, HttpServletResponse response) {
        UserEntity userEntity = userRepo.findByUserId(loginRequest.getUserId())
                .orElseThrow(()->new NotFoundException("cannot find matched id", ErrorCode.NOT_FOUND_EXCEPTION));
        if (!passwordEncoder.matches(loginRequest.getUserPw(), userEntity.getPassword()))
            throw new UnAuthorizedException("This password is incorrect", ErrorCode.UNAUTHORIZED_EXCEPTION);


        String AT = jwtTokenProvider.createAccessToken(userEntity.getUserId(), userEntity.getUserRole());
        String RT = jwtTokenProvider.createRefreshToken(userEntity.getUserId(), userEntity.getUserRole());

        userEntity.updateRefreshToken(RT);

        jwtTokenProvider.setHeaderAccessToken(response,AT);
        jwtTokenProvider.setHeaderRefreshToken(response,RT);

    }

    @Transactional
    public void register(RegisterRequestDto registerRequest, HttpServletResponse response) {
        if (userRepo.existsByUserId(registerRequest.getUserId()))
            throw new DuplicateException("Duplicated userId", ErrorCode.DUPLICATE_EXCEPTION);
        else if (userRepo.existsByNickName(registerRequest.getNickName())) {
            throw new DuplicateException("Duplicated nickName", ErrorCode.DUPLICATE_EXCEPTION);
        }

        String AT = jwtTokenProvider.createAccessToken(registerRequest.getUserId(), UserRole.Normal);
        String RT = jwtTokenProvider.createRefreshToken(registerRequest.getUserId(), UserRole.Normal);

        UserEntity userEntity = UserEntity.builder()
                .nickName(registerRequest.getUserName())
                .birth(registerRequest.getBirthDay())
                .email(registerRequest.getEmail())
                .userId(registerRequest.getUserId())
                .userRole(UserRole.Normal)
                .password(registerRequest.getUserPw() != null ? passwordEncoder.encode(registerRequest.getUserPw()) : null)
                .nickName(registerRequest.getNickName())
                .refreshToken(RT)
                .build();

        userRepo.save(userEntity);

        jwtTokenProvider.setHeaderAccessToken(response,AT);
        jwtTokenProvider.setHeaderRefreshToken(response,RT);

    }
}
