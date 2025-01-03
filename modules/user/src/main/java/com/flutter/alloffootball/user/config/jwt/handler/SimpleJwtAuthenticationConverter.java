package com.flutter.alloffootball.user.config.jwt.handler;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;

public abstract class SimpleJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

    @Override
    public final AbstractAuthenticationToken convert(Jwt jwt) { // 3
        AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt); // 4
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        return convert(jwt, authorities); // 5
    }

    public abstract AbstractAuthenticationToken convert(Jwt jwt, Collection<GrantedAuthority> authorities);
}
