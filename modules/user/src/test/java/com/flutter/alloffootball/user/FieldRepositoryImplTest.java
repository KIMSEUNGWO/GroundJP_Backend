package com.flutter.alloffootball.user;

import com.flutter.alloffootball.common.exception.FieldError;
import com.flutter.alloffootball.common.exception.FieldException;
import com.flutter.alloffootball.user.config.MockConfig;
import com.flutter.alloffootball.user.config.TestConfig;
import com.flutter.alloffootball.user.repository.FieldRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;

@Import({TestConfig.class, MockConfig.class})
@DataJpaTest
class FieldRepositoryImplTest {

    @Autowired
    private FieldRepository fieldRepository;

    @Test
    @DisplayName("존재하지 않는 구장을 검색하면 예외가 발생한다.")
    void 구장_정보_조회_예외() {
        // given
        long fieldId = 0L;

        // then
        assertThatThrownBy(() ->  fieldRepository.findById(fieldId))
            .isInstanceOf(FieldException.class)
            .satisfies(exception -> {
               FieldException fieldException = (FieldException) exception;
               assertThat(fieldException.getError()).isEqualTo(FieldError.FIELD_NOT_EXISTS);
            });

    }

}