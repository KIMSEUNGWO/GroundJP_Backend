package com.flutter.alloffootball.common.domain.user;

import com.flutter.alloffootball.common.domain.BaseEntityImage;
import jakarta.persistence.*;

@Entity
@Table(name = "PROFILE")
public class Profile extends BaseEntityImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFILE_ID")
    private Long id;

}