package com.flutter.alloffootball.common.domain.field;

import com.flutter.alloffootball.common.domain.Favorite;
import com.flutter.alloffootball.common.domain.match.Match;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FIELD")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FIELD_ID")
    private Long id;

    @Setter
    private String title;

    @Lob @Setter
    private String description;

    @Embedded
    private Address address;

    @Embedded
    private FieldData fieldData;

    @Builder.Default
    @OneToMany(mappedBy = "field", orphanRemoval = true)
    private List<FieldImage> fieldImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "field", orphanRemoval = true)
    private List<Match> matchList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "field", orphanRemoval = true)
    private List<Favorite> favoriteList = new ArrayList<>();

}
