package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByAccount(String account);
}
