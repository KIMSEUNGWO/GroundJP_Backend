package com.flutter.alloffootball.user.config;

import com.flutter.alloffootball.common.jparepository.*;
import com.flutter.alloffootball.user.component.CouponCalculator;
import com.flutter.alloffootball.user.querydsl.*;
import com.flutter.alloffootball.user.repository.*;
import com.flutter.alloffootball.user.service.FieldService;
import com.flutter.alloffootball.user.service.FieldServiceImpl;
import com.flutter.alloffootball.user.service.OrderService;
import com.flutter.alloffootball.user.service.OrderServiceImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

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
    private JpaUserCouponRepository jpaUserCouponRepository;
    @Autowired
    private JpaFavoriteRepository jpaFavoriteRepository;
    @Autowired
    private EntityManager em;

    @Bean
    JPAQueryFactory query() {
        return new JPAQueryFactory(em);
    }
    @Bean
    OrderService orderService() {
        return new OrderServiceImpl(paymentRepository(), userRepository(), matchRepository(), orderRepository(), userCouponRepository());
    }

    @Bean
    PaymentRepository paymentRepository() {
        return new PaymentRepositoryImpl(jpaCashRepository);
    }

    @Bean
    FieldRepository fieldRepository() {
        return new FieldRepositoryImpl(jpaFieldRepository);
    }
    @Bean
    FieldService fieldService() {
        return new FieldServiceImpl(queryDslFieldRepository(), fieldRepository(), jpaFavoriteRepository);
    }

    @Bean
    UserRepository userRepository() {
        return new UserRepositoryImpl(jpaUserRepository);
    }

    @Bean
    MatchRepository matchRepository() {
        return new MatchRepositoryImpl(jpaMatchRepository, queryDslMatchRepository());
    }

    @Bean
    QueryDslMatchRepository queryDslMatchRepository() {
        return new QueryDslMatchRepositoryImpl(query());
    }

    @Bean
    QueryDslFieldRepository queryDslFieldRepository() {
        return new QueryDslFieldRepositoryImpl(query());
    }
    @Bean
    QueryDslOrderRepository queryDslOrderRepository() {
        return new QueryDslOrderRepositoryImpl(query());
    }

    @Bean
    OrderRepository orderRepository() {
        return new OrderRepositoryImpl(jpaOrderRepository, queryDslOrderRepository());
    }

    @Bean
    CashRepository cashRepository() {
        return new CashRepositoryImpl(jpaCashRepository);
    }

    @Bean
    CouponCalculator couponCalculator() {
        return new CouponCalculator();
    }

    @Bean
    UserCouponRepository userCouponRepository() {
        return new UserCouponRepositoryImpl(jpaUserCouponRepository, couponCalculator());
    }
}
