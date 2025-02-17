package com.gamza.sportry.core.security;


import com.gamza.sportry.core.error.ErrorCode;
import com.gamza.sportry.core.error.exception.NotFoundException;
import com.gamza.sportry.core.error.exception.UnAuthorizedException;
import com.gamza.sportry.entity.UserEntity;
import com.gamza.sportry.entity.custom.UserRole;
import com.gamza.sportry.repo.UserRepo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final CustomUserDetailService customUserDetailService;
    private final UserRepo userRepo;

    @Value("${jwt.secretKey}")
    private String secretKey;

    // 액세스 토큰 유효시간
    @Value("${jwt.accessTokenExpiration}")
    private long accessTokenValidTime;

    // 리프레시 토큰 유효시간
    @Value("${jwt.refreshTokenExpiration}")
    private long refreshTokenValidTime;

    public String resolveAT(HttpServletRequest request) {
        if (request.getHeader("Authorization") != null )
            return request.getHeader("Authorization").substring(7);
        return null;
    }

    public String resolveRT(HttpServletRequest request) {
        if (request.getHeader("refreshToken") != null )
            return request.getHeader("refreshToken").substring(7);
        return null;
    }
    public boolean validateToken(String jwtToken) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            System.out.println("왜이러는 거임");
            throw new UnAuthorizedException("토큰 만료", ErrorCode.UNAUTHORIZED_EXCEPTION);
        }
    }
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String getUserId(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build();

        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }
    public Map<String, String> getTokenBody(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build();
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Map<String, String> claimsBody = new HashMap<>();
        claimsBody.put("role", (String) claims.get("role"));
        claimsBody.put("userImg", (String) claims.get("userImg"));
        return claimsBody;
    }
    public String createAccessToken(String userId, UserRole userRole) {

            return this.createToken(userId, userRole, accessTokenValidTime);
    }

    // Refresh Token 생성.
    public String createRefreshToken(String userId, UserRole userRole) {
            return this.createToken(userId, userRole, refreshTokenValidTime);
    }

    // Create token
    private String createToken(String userId, UserRole userRole, long tokenValid) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", userRole.toString());
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(date) // 발행 시간 저장
                .setExpiration(new Date(date.getTime() + tokenValid)) // 토큰 유효 시간 저장
                .signWith(key, SignatureAlgorithm.HS256) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성
    }
    public String refreshAccessToken(String refreshToken) {
        this.validateToken(refreshToken);

        UserEntity userEntity = userRepo.findByUserId(this.getUserId(refreshToken))
                .orElseThrow(()-> new NotFoundException("force re-login", ErrorCode.NOT_FOUND_EXCEPTION));

        if (!userEntity.getRefreshToken().equals(refreshToken))
            throw new UnAuthorizedException("force re-login.",ErrorCode.UNAUTHORIZED_EXCEPTION);

        return this.createAccessToken(userEntity.getUserId(), userEntity.getUserRole());
    }
    //redis 사용 연계로 블랙리스트 구현
    //public void expireToken(){}

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", "bearer "+ accessToken);
    }

    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("refreshToken", "bearer "+ refreshToken);
    }
    public String resolveAccessToken(HttpServletRequest request) {
        if (request.getHeader("Authorization") != null )
            return request.getHeader("Authorization").substring(7);
        return null;
    }

    // Request의 Header에서 RefreshToken 값을 가져옵니다. "refreshToken" : "token"
    public String resolveRefreshToken(HttpServletRequest request) {
        if (request.getHeader("refreshToken") != null )
            return request.getHeader("refreshToken").substring(7);
        return null;
    }
}
