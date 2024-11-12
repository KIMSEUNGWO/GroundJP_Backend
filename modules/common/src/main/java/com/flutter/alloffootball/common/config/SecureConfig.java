package com.flutter.alloffootball.common.config;

import com.flutter.alloffootball.common.enums.Authority;
import com.flutter.alloffootball.common.enums.Role;
import com.flutter.alloffootball.common.filter.FlutterAuthorizationFilter;
import com.flutter.alloffootball.common.filter.JwtAuthorizationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;

import java.io.IOException;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecureConfig {

    private final FlutterAuthorizationFilter flutterAuthorizationFilter;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(flutterAuthorizationFilter, SecurityContextHolderFilter.class);
        http.addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(request ->
            request
                .requestMatchers("/user/**", "/order/**").authenticated()
                .requestMatchers("/admin/login").permitAll()
                .requestMatchers("/admin/user/**").hasAnyAuthority(Authority.SUPER_ADMIN.name(), Authority.HR_ADMIN.name())
                .requestMatchers("/admin/match/**").hasAnyAuthority(Authority.SUPER_ADMIN.name(), Authority.MATCH_ADMIN.name())
                .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                .anyRequest().permitAll()
        );


        http.formLogin(formLogin ->
            formLogin
                .loginPage("/admin/login")
                .defaultSuccessUrl("/admin/field")
        );

        http.logout(formLogout ->
            formLogout
                .logoutUrl("/admin/logout")
        );

        return http.build();
    }

    private void executeMessage(HttpServletResponse response, String message) throws IOException {
        String redirect = "<script> alert('" + message + "'); </script>";
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(redirect);
    }

}