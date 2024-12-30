package com.flutter.alloffootball.admin.config.security;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.domain.admin.Admin;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class AdminUserDetails implements UserDetails {
    
    private final Admin admin;
    private final User user;

    public AdminUserDetails(Admin admin) {
        this.admin = admin;
        this.user = admin.getUser();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
            new SimpleGrantedAuthority(Role.ADMIN.getRoleName()),
            new SimpleGrantedAuthority(admin.getRoleAdmin().getRoleName())
        );
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
