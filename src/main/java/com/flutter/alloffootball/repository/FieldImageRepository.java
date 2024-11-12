package com.flutter.alloffootball.repository;

import com.flutter.alloffootball.common.domain.field.FieldImage;

import java.util.List;

public interface FieldImageRepository {
    void saveAll(List<FieldImage> saveImages);

    List<FieldImage> findAllById(List<Long> removeImages);

    void deleteAllById(List<Long> removeImages);
}
