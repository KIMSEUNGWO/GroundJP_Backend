package com.flutter.alloffootball.user.config.jwt;

import com.flutter.alloffootball.common.component.ResponseToken;
import com.flutter.alloffootball.common.domain.Token;
import com.flutter.alloffootball.common.domain.TokenId;
import com.flutter.alloffootball.common.jparepository.JpaTokenRepository;
import com.flutter.alloffootball.user.config.jwt.dto.TokenDto;
import com.flutter.alloffootball.user.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final int JWT_ACCESS_TIME = 10;
    private final int JWT_REFRESH_TIME = 60 * 60 * 24;

    public TokenDto generateAccessToken(TokenId tokenId, long userId,
                                         Collection<? extends GrantedAuthority> authorities) {
        String token = createToken(tokenId, userId, authorities, JWT_ACCESS_TIME);
        return new TokenDto(token, Instant.now().plusSeconds(JWT_ACCESS_TIME));
    }

    public TokenDto generateRefreshToken(TokenId tokenId, long userId,
                                        Collection<? extends GrantedAuthority> authorities) {
        String token = createToken(tokenId, userId, authorities, JWT_REFRESH_TIME);
        return new TokenDto(token, Instant.now().plusSeconds(JWT_REFRESH_TIME));
    }

    // 토큰 생성
    private String createToken(TokenId tokenId,
                               long userId,
                               Collection<? extends GrantedAuthority> authorities,
                               int expirationPeriod) {
        Date now = new Date(System.currentTimeMillis());

        return Jwts.builder()
            .header()
            .keyId(UUID.randomUUID().toString())                            // 토큰 식별을 위한 키 ID
            .add("typ", "JWT")                                        // 토큰 타입
            .and()
            .issuer("TEST_JWT")                                         // 토큰 발급자
            .claim("iss", tokenId.getProvider())
            .claim("sub", tokenId.getProviderId())
            .claim("userId", userId)
            .claim("roles", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))                              // 권한 정보
            .issuedAt(now)                                                  // 토큰 발급 시간
            .expiration(new Date(now.getTime() + expirationPeriod * 1000L))   // 토큰 만료 시간
            .signWith(secretKey)  // HS512 알고리즘으로 서명
            .compact();
    }

}
