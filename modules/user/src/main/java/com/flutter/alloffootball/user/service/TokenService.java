package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.component.ResponseToken;
import com.flutter.alloffootball.common.domain.Token;
import com.flutter.alloffootball.common.domain.TokenId;
import com.flutter.alloffootball.common.jparepository.JpaTokenRepository;
import com.flutter.alloffootball.user.config.jwt.JwtTokenProvider;
import com.flutter.alloffootball.user.config.jwt.UserJwtToken;
import com.flutter.alloffootball.user.config.jwt.dto.TokenDto;
import com.flutter.alloffootball.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final JpaTokenRepository jpaTokenRepository;


    public TokenDto generateAccessToken(TokenId tokenId, long userId,
                                        Collection<? extends GrantedAuthority> authorities) {
        return jwtTokenProvider.generateAccessToken(tokenId, userId, authorities);
    }

    @Transactional
    public TokenDto generateRefreshToken(HttpServletRequest request,
                                       TokenId tokenId, long userId,
                                       Collection<? extends GrantedAuthority> authorities) {
        TokenDto refreshToken = jwtTokenProvider.generateRefreshToken(tokenId, userId, authorities);
        saveToken(request, tokenId, userId, refreshToken);
        return refreshToken;
    }

    @Transactional(readOnly = true)
    public String verifyRefreshTokenAndGenerateAccessToken(HttpServletRequest request, UserJwtToken userJwtToken) {
        Token token = verifyRefreshToken(request);
        TokenDto accessToken = jwtTokenProvider.generateAccessToken(token.getTokenId(), userJwtToken.getUserId(), userJwtToken.getAuthorities());
        return accessToken.token();
    }

    private Token verifyRefreshToken(HttpServletRequest request) {
        String refreshToken = extractTokenFromHeader(request);
        return jpaTokenRepository.findByRefreshToken(refreshToken)
            .filter(token -> !token.isRevoked())
            .orElseThrow(() -> new InvalidBearerTokenException("Invalid refresh token"));
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        return request
            .getHeader(HttpHeaders.AUTHORIZATION)
            .substring(7);
    }

    private void saveToken(HttpServletRequest request, TokenId tokenId, long userId, TokenDto token) {
        Token saveToken = Token.builder()
            .tokenId(tokenId)
            .user(userRepository.findById(userId))
            .refreshToken(token.token())
            .expiresAt(token.expires())
            .issuedAt(Instant.now())
            .ip(getIP(request))
            .os(getOS(request))
            .build();

        jpaTokenRepository.save(saveToken);
    }

    private String getIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private String getOS(HttpServletRequest request) {
        String userAgentString = request.getHeader(HttpHeaders.USER_AGENT).toLowerCase();
        System.out.println("userAgentString = " + userAgentString);
        if (userAgentString.contains("windows")) {
            return "Windows";
        } else if (userAgentString.contains("mac")) {
            return "Mac";
        } else if (userAgentString.contains("linux")) {
            return "Linux";
        } else if (userAgentString.contains("android")) {
            return "Android";
        } else if (userAgentString.contains("iphone") || userAgentString.contains("ipad")) {
            return "iOS";
        } else {
            return "Unknown";
        }
    }

    @Transactional
    public void invalidateRefreshToken(String refreshToken) {
        jpaTokenRepository.deleteByRefreshToken(refreshToken);
    }
}
