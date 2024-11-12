package com.flutter.alloffootball.admin.sercurity;

import com.flutter.alloffootball.admin.repository.AdminRepository;
import com.flutter.alloffootball.common.component.SecurityUtil;
import com.flutter.alloffootball.common.domain.admin.Admin;
import com.flutter.alloffootball.common.domain.user.Social;
import com.flutter.alloffootball.common.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AdminAuthenticationProvider implements AuthenticationProvider {

    private final AdminRepository adminRepository;
    private final SecurityUtil securityUtil;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("CustomAuthenticationProvider 시작");
        String account = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println("account = " + account);
        System.out.println("password = " + password);

        Admin admin = adminRepository.findByAccount(account);
        if (!encoder.matches(password, admin.getPassword())) throw new UsernameNotFoundException("User not found");
        User user = admin.getUser();
        Social social = user.getSocial();
        securityUtil.saveUserInSecurityContext(social.getSocialId(), social.getProvider());
        securityUtil.saveAdminInSecurityContext(admin);
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
