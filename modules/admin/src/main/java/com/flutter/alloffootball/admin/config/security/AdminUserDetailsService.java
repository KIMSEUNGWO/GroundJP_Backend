package com.flutter.alloffootball.admin.config.security;

import com.flutter.alloffootball.admin.repository.AdminRepository;
import com.flutter.alloffootball.common.domain.admin.Admin;
import com.flutter.alloffootball.common.jparepository.JpaAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("AdminUserDetailsService.loadUserByUsername");
        Admin admin = adminRepository.findByUsername(username);
        return new AdminUserDetails(admin);
    }
}
