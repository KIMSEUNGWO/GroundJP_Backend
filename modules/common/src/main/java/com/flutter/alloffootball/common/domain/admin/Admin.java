package com.flutter.alloffootball.common.domain.admin;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.RoleAdmin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ADMIN")
@Builder
@Getter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADMIN_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleAdmin roleAdmin;

    private String username;
    private String password;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
