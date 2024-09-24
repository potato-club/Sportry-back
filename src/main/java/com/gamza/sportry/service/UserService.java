package com.gamza.sportry.service;

import com.gamza.sportry.core.security.JwtTokenProvider;
import com.gamza.sportry.entity.UserEntity;
import com.gamza.sportry.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final JwtTokenProvider jwtTokenProvider;

    public UserEntity findUserByToken(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        return token == null ? null : userRepo.findByUserId(jwtTokenProvider.getUserId(token)).orElse(null);
    }

}
