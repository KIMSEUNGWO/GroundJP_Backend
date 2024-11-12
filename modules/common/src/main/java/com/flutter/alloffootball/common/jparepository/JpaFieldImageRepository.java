package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.field.FieldImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaFieldImageRepository extends JpaRepository<FieldImage, Long> {

    Optional<FieldImage> findByStoreName(String storeName);
}
