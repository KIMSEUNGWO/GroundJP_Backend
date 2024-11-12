package com.flutter.alloffootball.admin.repository;

import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.exception.FieldError;
import com.flutter.alloffootball.common.exception.FieldException;
import com.flutter.alloffootball.common.jparepository.JpaFieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FieldRepository {

    private final JpaFieldRepository jpaFieldRepository;

    public Field fieldFindById(long fieldId) {
        return jpaFieldRepository.findById(fieldId)
            .orElseThrow(() -> new FieldException(FieldError.FIELD_NOT_EXISTS));
    }
}
