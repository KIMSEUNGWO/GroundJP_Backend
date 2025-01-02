package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.domain.TokenId;
import com.flutter.alloffootball.common.enums.Role;
import com.flutter.alloffootball.user.api.SocialProfile;
import com.flutter.alloffootball.user.api.SocialVerifier;
import com.flutter.alloffootball.common.component.ResponseToken;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.user.config.jwt.UserJwtToken;
import com.flutter.alloffootball.user.config.jwt.annotataion.JwtToken;
import com.flutter.alloffootball.user.config.jwt.dto.TokenDto;
import com.flutter.alloffootball.user.dto.login.RegisterRequest;
import com.flutter.alloffootball.user.dto.login.SocialLoginDto;
import com.flutter.alloffootball.common.exception.BindingException;
import com.flutter.alloffootball.common.exception.DefaultError;
import com.flutter.alloffootball.user.service.TokenService;
import com.flutter.alloffootball.user.service.RegisterService;
import com.flutter.alloffootball.user.service.SocialService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/social")
public class SocialController {

    private final SocialService socialService;
    private final RegisterService registerService;
    private final TokenService tokenService;

    private final SocialVerifier socialVerifier;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Validated SocialLoginDto loginDto,
                                          BindingResult bindingResult,
                                          HttpServletRequest request) {

        if (bindingResult.hasErrors()) throw new BindingException(bindingResult);

        // Social API 에서 사용자 정보 검증 및 가져오기
        SocialProfile profile = socialVerifier.getProfile(loginDto.getSocialId(), loginDto.getProvider(), loginDto.getAccessToken());

        // 로그인 또는 회원가입
        Optional<User> findUser = socialService.socialLogin(loginDto);
        if (findUser.isEmpty()) return Response.ok(DefaultError.REGISTER);
        User user = findUser.get();

        var tokenId = new TokenId(loginDto.getProvider().name(), loginDto.getSocialId());
        var authorities = List.of(new SimpleGrantedAuthority(user.getRole().getRoleName()));

        TokenDto accessToken = tokenService.generateAccessToken(tokenId, user.getId(), authorities);
        TokenDto refreshToken = tokenService.generateRefreshToken(request, tokenId, user.getId(), authorities);

        return Response.ok(new ResponseToken(accessToken.token(), refreshToken.token()));
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Validated @RequestBody RegisterRequest registerRequest,
                                             BindingResult bindingResult,
                                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) throw new BindingException(bindingResult);

        // Line API 에서 사용자 정보 검증 및 가져오기
        SocialProfile profile = socialVerifier.getProfile(registerRequest.getSocialId(), registerRequest.getProvider(), registerRequest.getAccessToken());

        Long userId = registerService.register(registerRequest, profile);

        var tokenId = new TokenId(registerRequest.getProvider().name(), registerRequest.getSocialId());
        var authorities = List.of(new SimpleGrantedAuthority(Role.USER.getRoleName()));

        TokenDto accessToken = tokenService.generateAccessToken(tokenId, userId, authorities);
        TokenDto refreshToken = tokenService.generateRefreshToken(request, tokenId, userId, authorities);

        return Response.ok(new ResponseToken(accessToken.token(), refreshToken.token()));
    }

    /**
     * RefreshToken 으로 AccessToken 발급
     *
     * RefreshToken 이 정상인 경우 -> AccessToken 발급
     * RefreshToken 이 만료된 경우, revoked = true 인 경우 -> DB Token 삭제 후 401 (UNAUTHORIZED)
     * RefreshToken 이 DB에 없거나 잘못된 토큰인 경우 -> 401 (UNAUTHORIZED)
     *
     * 잘못된 토큰이거나 401, 만료된 경우 401 -> 처리할 필요 없음 알아서 해줌
     *
     * DB에 없거나 revoked = true인 경우만 확인하면됨
     *
     */
    @PostMapping("/token")
    public ResponseEntity<Response> refreshingAccessToken(HttpServletRequest request, @JwtToken UserJwtToken userJwtToken) {
        String accessToken = tokenService.verifyRefreshTokenAndGenerateAccessToken(request, userJwtToken);
        return Response.ok(accessToken);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Response> logout(@RequestBody String refreshToken) {
        tokenService.invalidateRefreshToken(refreshToken);
        return Response.ok();
    }
}
