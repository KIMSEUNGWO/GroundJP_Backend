package com.flutter.alloffootball.common.component;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsUtil {

    private final SecurityUtil securityUtil;
    private final JwtUtil jwtUtil;

    public CustomUserDetails getUserDetails(HttpServletRequest request) {
        try {
            String accessToken = jwtUtil.extractTokenFromHeader(request);
            jwtUtil.validateAccessToken(accessToken);
            securityUtil.saveUserInSecurityContext(accessToken);
            return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
