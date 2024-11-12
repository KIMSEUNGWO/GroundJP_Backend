package com.flutter.alloffootball.admin.repository;

import com.flutter.alloffootball.common.jparepository.JpaAdminRepository;
import com.flutter.alloffootball.common.domain.admin.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {

    private final JpaAdminRepository jpaAdminRepository;

    @Override
    public Admin findByAccount(String account) {
        return jpaAdminRepository.findByAccount(account)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
