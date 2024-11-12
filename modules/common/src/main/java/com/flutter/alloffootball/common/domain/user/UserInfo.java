package com.flutter.alloffootball.common.domain.user;

import com.flutter.alloffootball.common.enums.SexType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Enumerated(EnumType.STRING)
    private SexType sex;
    private LocalDate birth;
}
