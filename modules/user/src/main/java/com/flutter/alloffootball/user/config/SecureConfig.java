package com.flutter.alloffootball.user.config;

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
                .anyRequest().permitAll()
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