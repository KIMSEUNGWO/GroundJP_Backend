package com.flutter.alloffootball.common.component;

import com.flutter.alloffootball.common.domain.user.Social;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.exception.TokenError;
import com.flutter.alloffootball.common.exception.TokenException;
import com.flutter.alloffootball.common.exception.UserError;
import com.flutter.alloffootball.common.exception.UserException;
import com.flutter.alloffootball.common.jparepository.JpaUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    private final JpaUserRepository jpaUserRepository;
    private static final long ACCESS_TOKEN_EXPIRES_TIME = 60 * 60 * 24;
    private static final long REFRESH_TOKEN_EXPIRES_TIME = 60 * 60 * 24 * 30;
    private final SecretKey secretKey;

    @Autowired
    public JwtUtil(JpaUserRepository jpaUserRepository,
                   @Value("${jwt.secret-key}") String secretKey) {
        this.jpaUserRepository = jpaUserRepository;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generateAccessToken(User user) {
        return createToken(ACCESS_TOKEN_EXPIRES_TIME, user.getSocial());
    }

    public String generateRefreshToken(User user) {
        return createToken(REFRESH_TOKEN_EXPIRES_TIME, user.getSocial());
    }


    private String createToken(long expirationPeriod, Social userSocial) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userSocial.getSocialId());
        claims.put("iss", userSocial.getProvider().name());

        return Jwts.builder()
            .signWith(secretKey, Jwts.SIG.HS256)
            .expiration(new Date(System.currentTimeMillis() + (expirationPeriod * 1000)))
            .issuedAt(new Date(System.currentTimeMillis()))
            .claims(claims)
            .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

            return claimsResolver.apply(claims);
        } catch (ExpiredJwtException e) {
            throw new TokenException(TokenError.TOKEN_EXPIRED);
        }
    }



    public void validateAccessToken(String accessToken) {
        if (accessToken == null || accessToken.isEmpty() || accessToken.equalsIgnoreCase("null")) {
            throw new TokenException(TokenError.ACCESS_TOKEN_REQUIRE);
        }

        if (isExpiredToken(accessToken)) {
            throw new TokenException(TokenError.TOKEN_EXPIRED);
        }

    }

    public void validateRefreshToken(String refreshToken) {
        String refreshTokenInDB = jpaUserRepository.findBySocial_RefreshToken(refreshToken)
            .orElseThrow(() -> new UserException(UserError.USER_NOT_EXISTS))
            .getSocial()
            .getRefreshToken();

        if (isExpiredToken(refreshTokenInDB)) {
            throw new TokenException(TokenError.TOKEN_EXPIRED);
        }

    }

    private boolean isExpiredToken(String token) {
        Date expiraionDate = extractClaim(token, Claims::getExpiration);
        return expiraionDate.before(new Date());
    }

    @Transactional
    public ResponseToken initToken(User saveOrFindUser) {
        String accessToken = generateAccessToken(saveOrFindUser);
        String refreshToken = generateRefreshToken(saveOrFindUser);

        saveOrFindUser.getSocial().setRefreshToken(refreshToken);

        return new ResponseToken(accessToken, refreshToken);

    }


    public ResponseToken refreshingAccessToken(User user, String refreshToken) {
        String accessToken = generateAccessToken(user);
        return new ResponseToken(accessToken, refreshToken);
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        } else {
            throw new TokenException(TokenError.ACCESS_TOKEN_REQUIRE);
        }
    }

}
