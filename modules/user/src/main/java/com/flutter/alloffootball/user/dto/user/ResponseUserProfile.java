package com.flutter.alloffootball.user.dto.user;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.Provider;
import com.flutter.alloffootball.common.enums.SexType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ResponseUserProfile {

    private final long id;
    private final Provider provider;
    private final String image;
    private final String nickname;
    private final SexType sex;
    private final LocalDate birth;

    private final int couponCount;

    private final int cash;

    public ResponseUserProfile(User user) {
        id = user.getId();
        provider = user.getSocial().getProvider();
        image = user.getProfile().getThumbnailName();
        nickname = user.getNickname();
        sex = user.getUserInfo().getSex();
        birth = user.getUserInfo().getBirth();
        couponCount = user.possibleCouponList().size();
        cash = user.getCash();
    }
}
