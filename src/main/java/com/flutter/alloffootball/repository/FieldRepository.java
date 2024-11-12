package com.flutter.alloffootball.repository;

import com.flutter.alloffootball.common.domain.field.Field;

public interface FieldRepository {
    Field findById(long fieldId);

    void save(Field saveField);
}
