package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.Provider;

import java.util.Optional;

public interface SocialRepository {

    Optional<User> findBySocialIdAndProvider(String socialId, Provider provider);

}
