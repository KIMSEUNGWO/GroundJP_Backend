package com.flutter.alloffootball.mock;

import com.flutter.alloffootball.common.domain.user.Social;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.domain.user.UserInfo;
import com.flutter.alloffootball.common.enums.Provider;
import com.flutter.alloffootball.common.enums.Role;
import com.flutter.alloffootball.common.jparepository.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MockUser {

    private final JpaUserRepository jpaUserRepository;

    @Autowired
    public MockUser(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public User mockUser(UserInfo userInfo, Role role, int cash) {
        User saveUser = User.builder()
            .email("asdf@naver.com")
            .social(Social.builder()
                .socialId("asdf")
                .provider(Provider.LINE)
                .refreshToken("ASFASDFASDFASDFASDF")
                .build())
            .userInfo(userInfo)
            .role(role)
            .nickname("asdf")
            .cash(cash)
            .build();
        return jpaUserRepository.save(saveUser);
    }
}
