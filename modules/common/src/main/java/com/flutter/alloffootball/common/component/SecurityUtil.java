package com.flutter.alloffootball.common.component;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.domain.admin.Admin;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.Provider;
import com.flutter.alloffootball.common.exception.TokenError;
import com.flutter.alloffootball.common.exception.TokenException;
import com.flutter.alloffootball.common.jparepository.JpaUserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUtil {

    private final JwtUtil jwtUtil;
    private final JpaUserRepository jpaUserRepository;


    public void saveUserInSecurityContext(String accessToken) {
        String socialId = jwtUtil.extractClaim(accessToken,  Claims::getSubject);
        String socialProvider = jwtUtil.extractClaim(accessToken, Claims::getIssuer);
        saveUserInSecurityContext(socialId, Provider.from(socialProvider));
    }

    public void saveUserInSecurityContext(String socialId, Provider provider) {
        if (socialId == null || provider == null) throw new TokenException(TokenError.TOKEN_EXPIRED);

        UserDetails userDetails = loadUserBySocialIdAndSocialProvider(socialId, provider);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private UserDetails loadUserBySocialIdAndSocialProvider(String socialId, Provider provider) {
        return jpaUserRepository.findBySocial_SocialIdAndSocial_Provider(socialId, provider)
            .map(CustomUserDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private UsernamePasswordAuthenticationToken getAuthentication(User user) {
        UserDetails userDetails = new CustomUserDetails(user);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


//    public void saveAdminInSecurityContext(Admin admin) {
//        UserDetails userDetails = new AdminUserDetails(admin);
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authentication);
//        SecurityContextHolder.setContext(context);
//    }
}