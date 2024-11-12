package com.flutter.alloffootball.common.domain.field;

import com.flutter.alloffootball.common.enums.region.Region;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Embeddable
public class Address {

    private String address;

    @Enumerated(EnumType.STRING)
    private Region region;

    private String link;
}
