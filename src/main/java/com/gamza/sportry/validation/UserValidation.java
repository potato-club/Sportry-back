package com.gamza.sportry.validation;

import com.gamza.sportry.core.error.ErrorCode;
import com.gamza.sportry.core.error.exception.NotFoundException;
import com.gamza.sportry.entity.UserEntity;
import com.gamza.sportry.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserService userService;

    public UserEntity isPresentUser(HttpServletRequest request) {
        UserEntity user = userService.findUserByToken(request);
        if (user == null)
            throw new NotFoundException("찾을 수 없는 유저입니다", ErrorCode.NOT_FOUND_EXCEPTION);
        return user;
    }

}
