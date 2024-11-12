package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.admin.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNoticeRepository extends JpaRepository<Notice, Long> {
}
