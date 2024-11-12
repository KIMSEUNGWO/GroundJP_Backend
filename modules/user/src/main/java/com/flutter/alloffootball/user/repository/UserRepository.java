package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.user.User;


public interface UserRepository {
    User findByRefreshToken(String refreshToken);

    User save(User user);

    User findById(long userId);

    boolean existsByNickname(String nickname);
}
