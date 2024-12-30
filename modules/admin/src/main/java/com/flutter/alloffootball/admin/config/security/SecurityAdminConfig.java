package com.flutter.alloffootball.admin.config.security;

import com.flutter.alloffootball.common.enums.RoleAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityAdminConfig {


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(request -> request
//            .requestMatchers(CorsUtils::isPreFlightRequest)
                .requestMatchers("/css/**", "/js/**", "/images/**", "/font/**").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/user/**").hasAnyRole(RoleAdmin.SUPER_ADMIN.name(), RoleAdmin.HR_ADMIN.name())
                .requestMatchers("/match/**").hasAnyRole(RoleAdmin.SUPER_ADMIN.name(), RoleAdmin.MATCH_ADMIN.name())
                .anyRequest().authenticated()
        )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/field")
        )
            .logout(formLogout -> formLogout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
        );

        ;
        return http.build();
    }


}
