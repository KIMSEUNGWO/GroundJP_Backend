package com.flutter.alloffootball.user.enums;

import com.flutter.alloffootball.common.enums.RefundPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class RefundPolicyTest {

    @ParameterizedTest
    @MethodSource("twoDayBefore")
    @DisplayName("경기시작일 2일전은 무료취소 정책이 적용된다.")
    void 무료취소_환불정책(LocalDateTime now) {
        // given
        LocalDateTime matchDate = LocalDateTime.of(2024, 8, 20, 18, 0);

        // when
        RefundPolicy execute = RefundPolicy.getPolicy(now, matchDate);

        // then
        assertThat(execute).isEqualTo(RefundPolicy.P100);

    }

    static Stream<LocalDateTime> twoDayBefore() {
        return Stream.of(
            LocalDateTime.of(2024, 8, 18, 18, 0),
            LocalDateTime.of(2024, 8, 18, 17, 59, 59),
            LocalDateTime.of(2024, 8, 1, 0, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("oneDayBefore")
    @DisplayName("경기시작일 1일전은 80% 정책이 적용된다.")
    void P80취소_환불정책(LocalDateTime now) {
        // given
        LocalDateTime matchDate = LocalDateTime.of(2024, 8, 20, 18, 0);

        // when
        RefundPolicy execute = RefundPolicy.getPolicy(now, matchDate);

        // then
        assertThat(execute).isEqualTo(RefundPolicy.P80);

    }

    static Stream<LocalDateTime> oneDayBefore() {
        return Stream.of(
            LocalDateTime.of(2024, 8, 18, 18, 0, 1),
            LocalDateTime.of(2024, 8, 19, 17, 59, 59)
        );
    }

    @ParameterizedTest
    @MethodSource("dDayBefore")
    @DisplayName("경기시작 1일이내는 20% 정책이 적용된다.")
    void P20취소_환불정책(LocalDateTime now) {
        // given
        LocalDateTime matchDate = LocalDateTime.of(2024, 8, 20, 18, 0);

        // when
        RefundPolicy execute = RefundPolicy.getPolicy(now, matchDate);

        // then
        assertThat(execute).isEqualTo(RefundPolicy.P20);

    }

    static Stream<LocalDateTime> dDayBefore() {
        return Stream.of(
            LocalDateTime.of(2024, 8, 19, 18, 18, 0),
            LocalDateTime.of(2024, 8, 19, 23, 59, 59),
            LocalDateTime.of(2024, 8, 20, 16, 0, 0),
            LocalDateTime.of(2024, 8, 20, 16, 30, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("min90Before")
    @DisplayName("경기시작 90분 이내는 환불불가 정책이 적용된다.")
    void 환불불가_환불정책(LocalDateTime now) {
        // given
        LocalDateTime matchDate = LocalDateTime.of(2024, 8, 20, 18, 0);

        // when
        RefundPolicy execute = RefundPolicy.getPolicy(now, matchDate);

        // then
        assertThat(execute).isEqualTo(RefundPolicy.P0);

    }

    static Stream<LocalDateTime> min90Before() {
        return Stream.of(
            LocalDateTime.of(2024, 8, 20, 16, 30, 1),
            LocalDateTime.of(2024, 8, 20, 17, 0, 0),
            LocalDateTime.of(2024, 8, 20, 20, 0, 0)
        );
    }

}