package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.field.FieldImage;
import com.flutter.alloffootball.common.jparepository.JpaFieldImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FieldImageRepositoryImpl implements FieldImageRepository {

    private final JpaFieldImageRepository jpaFieldImageRepository;

    @Override
    public void saveAll(List<FieldImage> saveImages) {
        jpaFieldImageRepository.saveAll(saveImages);
    }

    @Override
    public List<FieldImage> findAllById(List<Long> removeImages) {
        return jpaFieldImageRepository.findAllById(removeImages);
    }

    @Override
    public void deleteAllById(List<Long> removeImages) {
        jpaFieldImageRepository.deleteAllById(removeImages);
    }
}
