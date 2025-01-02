package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.Token;
import com.flutter.alloffootball.common.domain.TokenId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaTokenRepository extends JpaRepository<Token, TokenId> {

    Optional<Token> findByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
}
