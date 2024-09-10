package com.gamza.sportry.core.security;


import com.gamza.sportry.core.error.JwtErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        log.info(path);
        if (path.contains("/swagger") || path.contains("/v3/api-docs")
                || path.startsWith("/auth") || path.startsWith("/error")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String AT = jwtTokenProvider.resolveAT(request);
        String RT = jwtTokenProvider.resolveRT(request);
        JwtErrorCode errorCode;

        try {
            if (AT == null && RT != null) {
                if (path.contains("/refresh"))
                    filterChain.doFilter(request, response);
            } else {
                if (jwtTokenProvider.validateToken(AT))
                    this.setAuthentication(AT);
            }

        } catch (MalformedJwtException e) {
            errorCode = JwtErrorCode.INVALID_JWT_TOKEN;
            setResponse(response, errorCode);
            return;
        } catch (ExpiredJwtException e) {
            errorCode = JwtErrorCode.JWT_TOKEN_EXPIRED;
            setResponse(response, errorCode);
            return;
        } catch (UnsupportedJwtException e) {
            errorCode = JwtErrorCode.UNSUPPORTED_JWT_TOKEN;
            setResponse(response, errorCode);
            return;
        } catch (IllegalArgumentException e) {
            errorCode = JwtErrorCode.EMPTY_JWT_CLAIMS;
            setResponse(response, errorCode);
            return;
        } catch (SignatureException e) {
            errorCode = JwtErrorCode.JWT_SIGNATURE_MISMATCH;
            setResponse(response, errorCode);
            return;
        } catch (RuntimeException e) {
            errorCode = JwtErrorCode.JWT_COMPLEX_ERROR;
            log.error("Unexpected error: {}", e.getMessage(), e);
            setResponse(response, errorCode);
            return;
        }
        filterChain.doFilter(request, response);
    }
    private void setAuthentication(String accessToken) {
        // 토큰으로부터 유저 정보를 받아옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        // SecurityContext 에 Authentication 객체를 저장합니다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    private void setResponse(HttpServletResponse response, JwtErrorCode errorCode) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        json.put("code", errorCode.getCode());
        json.put("message", errorCode.getMessage());

        response.getWriter().print(json);
        response.getWriter().flush();
    }
}
