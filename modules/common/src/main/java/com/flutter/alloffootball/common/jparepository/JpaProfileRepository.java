package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.user.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProfileRepository extends JpaRepository<Profile, Long> {
}
