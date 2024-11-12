package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.user.dto.login.SocialLoginDto;
import com.flutter.alloffootball.common.exception.TokenError;
import com.flutter.alloffootball.common.exception.TokenException;
import com.flutter.alloffootball.user.repository.SocialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SocialServiceImpl implements SocialService {

    private final SocialRepository socialRepository;

    @Override
    public Optional<User> socialLogin(SocialLoginDto loginDto) {
        return socialRepository
            .findBySocialIdAndProvider(loginDto.getSocialId(), loginDto.getProvider());
    }

    @Override
    public User getUserInfoByUsingRefreshToken(String refreshToken) {
        return socialRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new TokenException(TokenError.REFRESH_TOKEN_EXPIRED));
    }
}
