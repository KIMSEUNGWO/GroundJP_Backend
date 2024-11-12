package com.flutter.alloffootball.common.domain.admin;

import com.flutter.alloffootball.common.domain.BaseEntityTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NOTICE")
@Builder
@Getter
public class Notice extends BaseEntityTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID")
    private Long id;

    private String title;
    private String content;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
