package com.flutter.alloffootball.user;


import com.flutter.alloffootball.common.exception.*;
import com.flutter.alloffootball.user.config.MockConfig;
import com.flutter.alloffootball.user.config.TestConfig;
import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.domain.user.UserInfo;
import com.flutter.alloffootball.user.dto.coupon.ResponseCouponUse;
import com.flutter.alloffootball.common.enums.Role;
import com.flutter.alloffootball.common.enums.SexType;
import com.flutter.alloffootball.common.jparepository.JpaUserCouponRepository;
import com.flutter.alloffootball.user.mock.MockCreator;
import com.flutter.alloffootball.user.repository.UserCouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@Import({TestConfig.class, MockConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
class UserCouponRepositoryImplTest {

    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private MockCreator mockCreator;
    @Autowired
    private JpaUserCouponRepository jpaUserCouponRepository;


    @Test
    @DisplayName("쿠폰 정상흐름")
    void 정상흐름() {
        int totalPrice = 10000;
        int discountPercent = 100;
        // given
        User user = mockCreator.mockUser(
            UserInfo.builder().birth(LocalDate.of(1996, 1, 10)).sex(SexType.MALE).build(),
            Role.USER,
            100000);
        UserCoupon userCoupon = mockCreator.mockUserCoupon(
            mockCreator.mockCoupon(discountPercent, 0),
            user,
            'N',
            LocalDateTime.now().plusDays(1)
        );


        // then
        ResponseCouponUse couponUse = userCouponRepository.useCoupon(userCoupon, user.getId(), LocalDateTime.now(), totalPrice);
        assertThat(couponUse).isNotNull();
        assertThat(couponUse.getDiscount()).isEqualTo(-1 * totalPrice);

    }

    @Test
    @DisplayName("만료된 쿠폰은 롤백되지않고 사용자가 소지한 쿠폰을 삭제한다.")
    void 만료된쿠폰은_삭제되어야한다() {
        int totalPrice = 10000;
        // given
        User user = mockCreator.mockUser(
            UserInfo.builder().birth(LocalDate.of(1996, 1, 10)).sex(SexType.MALE).build(),
            Role.USER,
            100000);
        UserCoupon userCoupon = mockCreator.mockUserCoupon(
            mockCreator.mockCoupon(100, 0),
            user,
            'N',
            LocalDateTime.now().minusDays(1)
        );

        Long userCouponId = userCoupon.getId();

        // then
        assertThatThrownBy(() -> userCouponRepository.useCoupon(userCoupon, user.getId(), LocalDateTime.now(), totalPrice))
            .isInstanceOf(CouponExpireException.class);

        assertThat(jpaUserCouponRepository.existsById(userCouponId)).isFalse();
    }

    @Test
    @DisplayName("다른 사용자에 의해 쿠폰이 사용되는 경우 예외가 발생해야한다.")
    void 쿠폰소지자_이외의_사용자가접근한경우() {
        int totalPrice = 10000;
        // given
        User user = mockCreator.mockUser(
            UserInfo.builder().birth(LocalDate.of(1996, 1, 10)).sex(SexType.MALE).build(),
            Role.USER,
            100000);
        UserCoupon userCoupon = mockCreator.mockUserCoupon(
            mockCreator.mockCoupon(100, 0),
            user,
            'N',
            LocalDateTime.now().plusDays(1)
        );

        // then
        assertThatThrownBy(() -> userCouponRepository.useCoupon(userCoupon, 123L, LocalDateTime.now(), totalPrice))
            .isInstanceOf(InvalidException.class)
            .satisfies(exception -> {
                assertThat(((InvalidException) exception).getError()).isEqualTo(DefaultError.INVALID_REQUEST);
            });

    }

    @Test
    @DisplayName("이미 사용된 쿠폰은 사용할 수 없다.")
    void 이미_사용된_쿠폰을_사용하려는경우() {
        int totalPrice = 10000;
        // given
        User user = mockCreator.mockUser(
            UserInfo.builder().birth(LocalDate.of(1996, 1, 10)).sex(SexType.MALE).build(),
            Role.USER,
            100000);
        UserCoupon userCoupon = mockCreator.mockUserCoupon(
            mockCreator.mockCoupon(100, 0),
            user,
            'Y',
            LocalDateTime.now().plusDays(1)
        );

        // then
        assertThatThrownBy(() -> userCouponRepository.useCoupon(userCoupon, user.getId(), LocalDateTime.now(), totalPrice))
            .isInstanceOf(CouponException.class)
            .satisfies(exception -> {
                assertThat(((CouponException) exception).getError()).isEqualTo(CouponError.COUPON_ALREADY_USE);
            });

    }

}