package com.flutter.alloffootball.common.domain.user;

import com.flutter.alloffootball.common.enums.Provider;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Social {

    private String socialId;

    @Enumerated(EnumType.STRING)
    private Provider provider;

}
