package com.flutter.alloffootball.service;

import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.exception.*;
import com.flutter.alloffootball.config.MockConfig;
import com.flutter.alloffootball.config.TestConfig;
import com.flutter.alloffootball.common.domain.Cash;
import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.domain.user.UserInfo;
import com.flutter.alloffootball.dto.order.RequestOrder;
import com.flutter.alloffootball.dto.order.ResponseOrderResult;
import com.flutter.alloffootball.common.enums.CashType;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.Role;
import com.flutter.alloffootball.common.enums.SexType;
import com.flutter.alloffootball.common.jparepository.JpaCashRepository;
import com.flutter.alloffootball.common.jparepository.JpaUserCouponRepository;
import com.flutter.alloffootball.mock.MockCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@Import({TestConfig.class, MockConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderServiceImplTest {

    @Autowired
    private JpaCashRepository jpaCashRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MockCreator mockCreator;
    @Autowired
    private JpaUserCouponRepository jpaUserCouponRepository;

    @Test
    @DisplayName("쿠폰을 사용하지 않은 주문 정상처리")
    void 주문_정상처리() {

        int price = 20000;
        int matchTime = 2;
        int userCash = 30000;
        // given
        Match match = mockCreator.mockMatch(mockCreator.mockField(), LocalDateTime.now(), MatchStatus.OPEN, null, matchTime, 3, 6, price);
        User user = mockCreator.mockUser(
            UserInfo.builder().birth(LocalDate.of(1996, 1, 10)).sex(SexType.MALE).build(),
            Role.USER,
            userCash);
        RequestOrder requestOrder = new RequestOrder();
        requestOrder.setCouponId(null);
        requestOrder.setMatchId(match.getId());

        // then
        ResponseOrderResult order = orderService.order(requestOrder, user.getId(), LocalDateTime.now());

        // when
        // 영수증 검증
        assertThat(order)
            .extracting(
                ResponseOrderResult::getTotalPrice,
                ResponseOrderResult::getCoupon,
                ResponseOrderResult::getFinalPrice,
                ResponseOrderResult::getRemainCash)
            .containsExactly(
                price,
                null,
                price,
                userCash - price);

        // Cash 사용
        List<Cash> receipt = jpaCashRepository.findAllByUser_Id(user.getId());
        assertThat(receipt.size()).isEqualTo(1);
        assertThat(receipt.get(0))
            .extracting(
                cash -> cash.getUser().getId(),
                Cash::getCashType,
                Cash::getCashUse,
                Cash::getCashNow)
            .containsExactly(
                user.getId(),
                CashType.USE,
                -1 * price,
                userCash - price
            );


    }

    @Test
    @DisplayName("최종가격보다 캐시가 적어도 쿠폰을 사용하면 정상처리되어야한다.")
    void 쿠폰을_사용한_주문_정상처리() {

        int price = 20000;
        int matchTime = 2;
        int userCash = 0;
        // given
        Match match = mockCreator.mockMatch(
            mockCreator.mockField(),
            LocalDateTime.now(),
            MatchStatus.OPEN,
            null,
            matchTime,
            3,
            6,
            price
            );
        User user = mockCreator.mockUser(
            UserInfo.builder().birth(LocalDate.of(1996, 1, 10)).sex(SexType.MALE).build(),
            Role.USER,
            userCash);
        UserCoupon userCoupon = mockCreator.mockUserCoupon(
            mockCreator.mockCoupon(100, 100),
            user,
            'N',
            LocalDateTime.now().plusDays(100)
        );

        RequestOrder requestOrder = new RequestOrder();
        requestOrder.setCouponId(userCoupon.getId());
        requestOrder.setMatchId(match.getId());

        // then
        ResponseOrderResult order = orderService.order(requestOrder, user.getId(), LocalDateTime.now());

        // when

        // 쿠폰 사용 처리
        assertThat(userCoupon.isUse()).isTrue();
        assertThat(order.getCoupon()).isNotNull();
        int discount = (int) (-1 * (price * ((double) userCoupon.getCoupon().getDiscountPer() / 100)));
        assertThat(order.getCoupon().getDiscount()).isEqualTo(discount);

        // 영수증 검증
        assertThat(order)
            .extracting(
                ResponseOrderResult::getTotalPrice,
                ResponseOrderResult::getFinalPrice,
                ResponseOrderResult::getRemainCash)
            .containsExactly(
                price,
                price + discount,
                userCash - (price + discount));

        // Cash 사용
        List<Cash> receipt = jpaCashRepository.findAllByUser_Id(user.getId());
        assertThat(receipt.size()).isEqualTo(1);
        assertThat(receipt.get(0))
            .extracting(
                cash -> cash.getUser().getId(),
                Cash::getCashType,
                Cash::getCashUse,
                Cash::getCashNow)
            .containsExactly(
                user.getId(),
                CashType.USE,
                price + discount,
                userCash - (price + discount)
            );


    }


    @Test
    @DisplayName("경기정보가 존재하지 않으면 예외가 발생한다.")
    void 주문시_예외상황_경기정보가_존재하지않음() {
        // given
        RequestOrder requestOrder = new RequestOrder();
        requestOrder.setCouponId(null);
        requestOrder.setMatchId(null);

        // then
        assertThatThrownBy(() -> orderService.order(requestOrder, 0L, LocalDateTime.now()))
            .isInstanceOf(MatchException.class)
            .satisfies(exception -> {
                assertThat(((MatchException) exception).getError()).isEqualTo(MatchError.MATCH_NOT_EXISTS);
            });
    }

    @Test
    @DisplayName("유저 정보가 존재하지않으면 예외가 발생한다.")
    void 주문시_예외상황_유저정보가_존재하지않음() {
        // given
        Match match = mockCreator.mockMatch(mockCreator.mockField(), LocalDateTime.now(), MatchStatus.OPEN, null, 2, 3, 6, 10000);

        RequestOrder requestOrder = new RequestOrder();
        requestOrder.setCouponId(null);
        requestOrder.setMatchId(match.getId());


        // then
        assertThatThrownBy(() -> orderService.order(requestOrder, 0L, LocalDateTime.now()))
            .isInstanceOf(UserException.class)
            .satisfies(exception -> {
                assertThat(((UserException) exception).getError()).isEqualTo(UserError.USER_NOT_EXISTS);
            });

    }

    @ParameterizedTest
    @ValueSource(ints = {19999, 10000, 10001, 9999})
    @DisplayName("캐시가 부족하면 예외가 발생한다.")
    void 주문시_캐시가_부족한경우(int userCash) {

        // 최종 금액 20000원
        int price = 20000;
        int matchTime = 2;

        // given
        Match match = mockCreator.mockMatch(mockCreator.mockField(), LocalDateTime.now(), MatchStatus.OPEN, null, matchTime, 3, 6, price);
        User user = mockCreator.mockUser(
            UserInfo.builder().birth(LocalDate.of(1996, 1, 10)).sex(SexType.MALE).build(),
            Role.USER,
            userCash);
        RequestOrder requestOrder = new RequestOrder();
        requestOrder.setCouponId(null);
        requestOrder.setMatchId(match.getId());


        // then
        assertThatThrownBy(() -> orderService.order(requestOrder, user.getId(), LocalDateTime.now()))
            .isInstanceOf(OrderException.class)
            .satisfies(exception -> {
                assertThat(((OrderException) exception).getError()).isEqualTo(OrderError.NOT_ENOUGH_CASH);
            });

    }

    @Test
    @DisplayName("같은 경기에 중복참가인경우 예외가 발생해야한다.")
    void 주문시_이미_경기가_마감된경우() {
        int price = 20000;
        int matchTime = 2;
        int userCash = 50000;
        // given
        Match match = mockCreator.mockMatch(
            mockCreator.mockField(),
            LocalDateTime.now(),
            MatchStatus.OPEN,
            null,
            matchTime,
            3,
            6,
            price
            );
        User user = mockCreator.mockUser(
            UserInfo.builder().birth(LocalDate.of(1996, 1, 10)).sex(SexType.MALE).build(),
            Role.USER,
            userCash);

        RequestOrder requestOrder = new RequestOrder();
        requestOrder.setCouponId(null);
        requestOrder.setMatchId(match.getId());

        // when
        orderService.order(requestOrder, user.getId(), LocalDateTime.now());

        // then
        assertThatThrownBy(() -> orderService.order(requestOrder, user.getId(), LocalDateTime.now()))
            .isInstanceOf(OrderException.class)
            .satisfies(exception -> {
                assertThat(((OrderException) exception).getError()).isEqualTo(OrderError.ALREADY_JOIN);
            });


    }

    @Test
    @DisplayName("마감된 경기는 참가할 수 없다.")
    void 경기가_이미_마감된_경우() {
        int price = 20000;
        int matchTime = 2;
        int userCash = 50000;
        // given
        Match match = mockCreator.mockMatch(
            mockCreator.mockField(),
            LocalDateTime.now(),
            MatchStatus.CLOSED,
            null,
            matchTime,
            3,
            6,
            price
            );
        User user = mockCreator.mockUser(
            UserInfo.builder().birth(LocalDate.of(1996, 1, 10)).sex(SexType.MALE).build(),
            Role.USER,
            userCash);

        RequestOrder requestOrder = new RequestOrder();
        requestOrder.setCouponId(null);
        requestOrder.setMatchId(match.getId());

        // then
        assertThatThrownBy(() -> orderService.order(requestOrder, user.getId(), LocalDateTime.now()))
            .isInstanceOf(OrderException.class)
            .satisfies(exception -> {
                assertThat(((OrderException) exception).getError()).isEqualTo(OrderError.ALREADY_CLOSED);
            });


    }


    @Test
    @DisplayName("성별이 일치하지 않은경우 예외가 발생해야한다.")
    void 경기_참가조건인_성별이_일차하지않은경우() {
        int price = 20000;
        int matchTime = 2;
        int userCash = 50000;
        // given
        Match match = mockCreator.mockMatch(
            mockCreator.mockField(),
            LocalDateTime.now(),
            MatchStatus.CLOSED,
            SexType.FEMALE,
            matchTime,
            3,
            6,
            price
            );
        User user = mockCreator.mockUser(
            UserInfo.builder().birth(LocalDate.of(1996, 1, 10)).sex(SexType.MALE).build(),
            Role.USER,
            userCash);

        RequestOrder requestOrder = new RequestOrder();
        requestOrder.setCouponId(null);
        requestOrder.setMatchId(match.getId());

        // then
        assertThatThrownBy(() -> orderService.order(requestOrder, user.getId(), LocalDateTime.now()))
            .isInstanceOf(OrderException.class)
            .satisfies(exception -> {
                assertThat(((OrderException) exception).getError()).isEqualTo(OrderError.NOT_MATCHED_SEX);
            });


    }

    @ParameterizedTest
    @ValueSource(ints = {0, 9999, 5000})
    @DisplayName("쿠폰을 사용해도 캐시가 부족하면 예외가 발생해야 한다.")
    void 쿠폰을_사용해도_캐시가_부족한_경우(int userCash) {

        int price = 20000;
        int matchTime = 2;
        int discountPer = 50;
        // given
        Match match = mockCreator.mockMatch(
            mockCreator.mockField(),
            LocalDateTime.now(),
            MatchStatus.OPEN,
            null,
            matchTime,
            3,
            6,
            price
            );
        User user = mockCreator.mockUser(
            UserInfo.builder().birth(LocalDate.of(1996, 1, 10)).sex(SexType.MALE).build(),
            Role.USER,
            userCash);
        UserCoupon userCoupon = mockCreator.mockUserCoupon(
            mockCreator.mockCoupon(discountPer, 100),
            user,
            'N',
            LocalDateTime.now().plusDays(100)
        );

        RequestOrder requestOrder = new RequestOrder();
        requestOrder.setCouponId(userCoupon.getId());
        requestOrder.setMatchId(match.getId());

        // when
        assertThatThrownBy(() -> orderService.order(requestOrder, user.getId(), LocalDateTime.now()))
            .isInstanceOf(OrderException.class)
            .satisfies(e -> {
                assertThat(((OrderException) e).getError()).isEqualTo(OrderError.NOT_ENOUGH_CASH);
            });

    }


}