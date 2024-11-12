package com.flutter.alloffootball.common.config.security;

import com.flutter.alloffootball.common.domain.admin.Admin;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class AdminUserDetails extends CustomUserDetails {
    
    private final Admin admin;
    public AdminUserDetails(Admin admin) {
        super(admin.getUser());
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>(super.getAuthorities());
        grantedAuthorities.add(new SimpleGrantedAuthority(admin.getAuthority().name()));
        return grantedAuthorities;
    }
}
