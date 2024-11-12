package com.flutter.alloffootball.querydsl;

import com.flutter.alloffootball.common.domain.field.Field;

import java.util.List;

public interface QueryDslFieldRepository {
    List<Field> search(String word);
}
