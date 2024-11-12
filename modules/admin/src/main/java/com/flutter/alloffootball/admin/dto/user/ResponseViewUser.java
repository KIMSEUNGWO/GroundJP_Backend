package com.flutter.alloffootball.admin.dto.user;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.Provider;
import com.flutter.alloffootball.common.enums.SexType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ResponseViewUser {

    private final long userId;
    private final String profile;
    private final String nickname;
    private final LocalDate birth;
    private final SexType sex;
    private final Provider social;
    private final int cash;
    private final LocalDateTime createDate;
    private final String status;

    public ResponseViewUser(User user) {
        this.userId = user.getId();
        this.profile = user.getProfile().getOriginalName();
        this.nickname = user.getNickname();
        this.birth = user.getUserInfo().getBirth();
        this.sex = user.getUserInfo().getSex();
        this.social = user.getSocial().getProvider();
        this.cash = user.getCash();
        this.createDate = user.getCreateDate();
        this.status = "정상";
    }
}
