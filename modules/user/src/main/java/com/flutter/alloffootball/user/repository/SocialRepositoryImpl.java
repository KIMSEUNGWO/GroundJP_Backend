package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.Provider;
import com.flutter.alloffootball.common.jparepository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SocialRepositoryImpl implements SocialRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> findBySocialIdAndProvider(String socialId, Provider provider) {
        return jpaUserRepository.findBySocial_SocialIdAndSocial_Provider(socialId, provider);
    }

}
