package com.flutter.alloffootball.admin.dto.user;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.Provider;
import com.flutter.alloffootball.common.enums.SexType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ResponseSearchUser {

    private final Long userId;
    private final String nickname;
    private final Provider social;
    private final String sex;
    private final LocalDate birth;
    private final LocalDateTime createDate;
    private final String status;

    public ResponseSearchUser(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.social = user.getSocial().getProvider();
        this.sex = user.getUserInfo().getSex() == SexType.MALE ? "남자" : "여자";
        this.birth = user.getUserInfo().getBirth();
        this.createDate = LocalDateTime.now();
        this.status = "정상";
    }
}
