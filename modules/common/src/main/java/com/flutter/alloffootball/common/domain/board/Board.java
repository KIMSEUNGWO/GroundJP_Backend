package com.flutter.alloffootball.common.domain.board;

import com.flutter.alloffootball.common.domain.BaseEntityTime;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.region.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "BOARD")
public class Board extends BaseEntityTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCHES_ID")
    private Match match;

    private String title;
    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private Region region;

    private LocalDateTime updateDate;

    public void update(String title, String content, Region region, Match match) {
        this.title = title;
        this.content = content;
        this.region = region;
        this.match = match;
        this.updateDate = LocalDateTime.now();
    }

    public boolean isOwner(Long userId) {
        return user.getId().equals(userId);
    }
}
