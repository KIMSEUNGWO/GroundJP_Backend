package com.flutter.alloffootball.component;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class CouponCalculatorTest {

    private final CouponCalculator couponCalculator = new CouponCalculator();

    @Test
    @DisplayName("0% 할인쿠폰은 price와 같다")
    void 쿠폰적용1() {
        // given
        int price = 10000;
        int discountPer = 0;

        // when
        int discount = couponCalculator.discount(price, discountPer);
        int totalPrice = couponCalculator.total(price, discountPer);

        // then
        assertThat(discount).isEqualTo(0);
        assertThat(totalPrice).isEqualTo(price);
    }

    @ParameterizedTest
    @DisplayName("100% 할인쿠폰은 price와 관계없이 최종가격이 0원이 되어야한다.")
    @ValueSource(ints = {0, 1, 1000, 2000, 3000, 60000, 100000})
    void 쿠폰적용2(int price) {
        // given
        int discountPer = 100;

        // when
        int discount = couponCalculator.discount(price, discountPer);
        int totalPrice = couponCalculator.total(price, discountPer);

        // then
        assertThat(discount).isEqualTo(price);
        assertThat(totalPrice).isEqualTo(0);
    }

    @ParameterizedTest
    @DisplayName("쿠폰 할인율이 0보다 작을경우 할인율 0%로 적용한다")
    @ValueSource(ints = {0, -1, -100, -10000})
    void 쿠폰적용3(int discountPer) {
        // given
        int price = 7654;

        // when
        int discount = couponCalculator.discount(price, discountPer);
        int totalPrice = couponCalculator.total(price, discountPer);

        // then
        assertThat(discount).isEqualTo(0);
        assertThat(totalPrice).isEqualTo(price);
    }

    @Test
    void 정상흐름() {
        // given
        int price = 10000;
        int discountPer = 50;

        // when
        int discount = couponCalculator.discount(price, discountPer);
        int totalPrice = couponCalculator.total(price, discountPer);

        // then
        assertThat(discount).isEqualTo(5000);
        assertThat(totalPrice).isEqualTo(5000);

    }

}