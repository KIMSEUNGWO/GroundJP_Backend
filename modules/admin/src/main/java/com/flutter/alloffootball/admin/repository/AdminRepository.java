package com.flutter.alloffootball.admin.repository;

import com.flutter.alloffootball.common.jparepository.JpaAdminRepository;
import com.flutter.alloffootball.common.domain.admin.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminRepository {

    private final JpaAdminRepository jpaAdminRepository;

    public Admin findByUsername(String username) {
        return jpaAdminRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
