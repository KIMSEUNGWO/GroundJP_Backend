package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.field.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFieldRepository extends JpaRepository<Field, Long> {
}
