package com.flutter.alloffootball.user.config;

import com.flutter.alloffootball.common.jparepository.*;
import com.flutter.alloffootball.user.mock.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockConfig {

    @Autowired
    private JpaFieldRepository jpaFieldRepository;
    @Autowired
    private JpaUserRepository jpaUserRepository;
    @Autowired
    private JpaMatchRepository jpaMatchRepository;
    @Autowired
    private JpaOrderRepository jpaOrderRepository;
    @Autowired
    private JpaCashRepository jpaCashRepository;
    @Autowired
    private JpaCouponRepository jpaCouponRepository;
    @Autowired
    private JpaUserCouponRepository jpaUserCouponRepository;

    @Bean
    MockCreator mockCreator() {
        return new MockCreator(mockField(), mockMatch(), mockUser(), mockCoupon(), mockUserCoupon());
    }
    @Bean
    MockMatch mockMatch() {
        return new MockMatch(jpaMatchRepository);
    }

    @Bean
    MockField mockField() {
        return new MockField(jpaFieldRepository);
    }

    @Bean
    MockUser mockUser() {
        return new MockUser(jpaUserRepository);
    }

    @Bean
    MockCoupon mockCoupon() {
        return new MockCoupon(jpaCouponRepository);
    }
    @Bean
    MockUserCoupon mockUserCoupon() {
        return new MockUserCoupon(jpaUserCouponRepository);
    }
}
