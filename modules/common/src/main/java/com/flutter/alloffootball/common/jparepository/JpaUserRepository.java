package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocial_SocialIdAndSocial_Provider(String socialId, Provider provider);

    boolean existsByNickname(String nickname);

    Page<User> findAllByNicknameContainingIgnoreCaseOrderByIdDesc(String nickname, Pageable pageable);
}
