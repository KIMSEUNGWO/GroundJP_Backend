package com.flutter.alloffootball.user;

import com.flutter.alloffootball.common.component.DataValidator;
import com.flutter.alloffootball.common.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class DataValidatorTest {

    private final DataValidator dataValidator;

    public DataValidatorTest() {
        this.dataValidator = new DataValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"aa", "aaa", "asdf", "asdfasdf", "12345678"})
    @DisplayName("닉네임은 2 - 8자만 허용한다.")
    void 닉네임_문자열_검증기_정상흐름(String nickname) {
        assertThatCode(() -> dataValidator.validNickname(nickname))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("닉네임이 null 또는 빈 문자열이면 정상흐름이다")
    void 닉네임_데이터가없어도_예외가_발생하지않는다() {
        // given
        String nickname = null;
        String nickname2 = "";

        // when
        assertThatCode(() -> dataValidator.validNickname(nickname))
            .doesNotThrowAnyException();
        assertThatCode(() -> dataValidator.validNickname(nickname2))
            .doesNotThrowAnyException();

    }

    @ParameterizedTest
    @ValueSource(strings = {"a a", "aaa  ", "  asdf", "a sdf asdf", "12345678 "})
    @DisplayName("띄어쓰기가 존재하는 경우 예외를 반환한다.")
    void 닉네임_문자열_검증기_예외1(String nickname) {
        assertThatThrownBy(() -> dataValidator.validNickname(nickname))
            .isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"anulla", "aasnull", "  asnulldf"})
    @DisplayName("닉네임에 null 문자열이 포함된 문자열은 사용할 수 없다.")
    void 닉네임_문자열_검증기_예외2(String nickname) {
        assertThatThrownBy(() -> dataValidator.validNickname(nickname))
            .isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ㅁㄴ,ㅇ", "!안녕", "  ++안녕", "[]\"aef"})
    @DisplayName("닉네임은 한국어,숫자,영어,일본어,한자가 아니면 예외가 발생한다.")
    void 닉네임_문자열_검증기_예외3(String nickname) {
        assertThatThrownBy(() -> dataValidator.validNickname(nickname))
            .isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"안녕하세요", "hihihi", "12345678", "足立区", "メッセージ"})
    @DisplayName("닉네임은 한국어,숫자,영어,일본어,한자만 사용 가능하다.")
    void 닉네임_문자열_검증기_정상흐름2(String nickname) {
        assertThatCode(() -> dataValidator.validNickname(nickname))
            .doesNotThrowAnyException();
    }


}