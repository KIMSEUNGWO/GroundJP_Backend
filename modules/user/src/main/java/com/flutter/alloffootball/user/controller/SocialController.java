package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.user.api.SocialProfile;
import com.flutter.alloffootball.user.api.SocialVerifier;
import com.flutter.alloffootball.common.component.JwtUtil;
import com.flutter.alloffootball.common.component.ResponseToken;
import com.flutter.alloffootball.common.component.SecurityUtil;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.user.dto.login.RegisterRequest;
import com.flutter.alloffootball.user.dto.login.SocialLoginDto;
import com.flutter.alloffootball.common.exception.BindingException;
import com.flutter.alloffootball.common.exception.DefaultError;
import com.flutter.alloffootball.user.service.RegisterService;
import com.flutter.alloffootball.user.service.SocialService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/social")
public class SocialController {

    private final SocialService socialService;
    private final RegisterService registerService;

    private final SocialVerifier socialVerifier;
    private final SecurityUtil securityUtil;
    private final JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Validated SocialLoginDto loginDto,
                                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) throw new BindingException(bindingResult);

        // Social API 에서 사용자 정보 검증 및 가져오기
        SocialProfile profile = socialVerifier.getProfile(loginDto.getSocialId(), loginDto.getProvider(), loginDto.getAccessToken());

        // 로그인 또는 회원가입
        Optional<User> findUser = socialService.socialLogin(loginDto);

        if (findUser.isEmpty()) return Response.ok(DefaultError.REGISTER);

        User user = findUser.get();

        securityUtil.saveUserInSecurityContext(loginDto.getSocialId(), loginDto.getProvider());

        ResponseToken responseToken = jwtUtil.initToken(user);

        return Response.ok(responseToken);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Validated @RequestBody RegisterRequest registerRequest,
                                             BindingResult bindingResult) {
        System.out.println("registerRequest = " + registerRequest);
        if (bindingResult.hasErrors()) throw new BindingException(bindingResult);

        // Line API 에서 사용자 정보 검증 및 가져오기
        SocialProfile profile = socialVerifier.getProfile(registerRequest.getSocialId(), registerRequest.getProvider(), registerRequest.getAccessToken());

        ResponseToken responseToken = registerService.register(registerRequest, profile);

        System.out.println("responseToken = " + responseToken);
        return Response.ok(responseToken);
    }

    @PostMapping("/token")
    public ResponseEntity<Response> refreshingAccessToken(HttpServletRequest request) {
        String refreshToken = jwtUtil.extractTokenFromHeader(request);
        jwtUtil.validateRefreshToken(refreshToken);

        User user = socialService.getUserInfoByUsingRefreshToken(refreshToken);
        ResponseToken responseToken = jwtUtil.refreshingAccessToken(user, refreshToken);
        return Response.ok(responseToken);
    }
}
