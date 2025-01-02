package com.flutter.alloffootball.user.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import java.time.Instant;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final SecretKey secretKey;

    /**
     * JWT 흐름
     *
     * 인증된 경우       만료또는 잘못된 토큰         권한불충분
     *
     * 통과            401(UNAUTHORIZED)       403(FORBIDDEN)
     *
     */

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(secretKey)
            .macAlgorithm(MacAlgorithm.HS512)
            .build();
    }

}
