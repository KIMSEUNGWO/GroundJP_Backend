package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.field.Field;

public interface FieldRepository {
    Field findById(long fieldId);

    void save(Field saveField);
}
