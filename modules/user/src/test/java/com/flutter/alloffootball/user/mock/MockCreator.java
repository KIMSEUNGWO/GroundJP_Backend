package com.flutter.alloffootball.user.mock;

import com.flutter.alloffootball.common.domain.coupon.Coupon;
import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.domain.user.UserInfo;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.Role;
import com.flutter.alloffootball.common.enums.SexType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MockCreator {

    private final MockField mockField;
    private final MockMatch mockMatch;
    private final MockUser mockUser;
    private final MockCoupon mockCoupon;
    private final MockUserCoupon mockUserCoupon;

    @Autowired
    public MockCreator(MockField mockField, MockMatch mockMatch, MockUser mockUser, MockCoupon mockCoupon, MockUserCoupon mockUserCoupon) {
        this.mockField = mockField;
        this.mockMatch = mockMatch;
        this.mockUser = mockUser;
        this.mockCoupon = mockCoupon;
        this.mockUserCoupon = mockUserCoupon;
    }



    public Field mockField() {
        return mockField.mockField();
    }
    public Match mockMatch(Field field, LocalDateTime matchDate, MatchStatus matchStatus, SexType matchSex, int matchTime, int matchCount, int personCount, int price) {
        return mockMatch.mockMatch(field, matchDate, matchStatus, matchSex, matchTime, matchCount, personCount, price);
    }
    public User mockUser(UserInfo userInfo, Role role, int cash) {
        return mockUser.mockUser(userInfo, role, cash);
    }

    public Coupon mockCoupon(int discountPer, int expireDay) {
        return mockCoupon.mockCoupon(discountPer, expireDay);
    }
    public UserCoupon mockUserCoupon(Coupon coupon, User user, char isUse, LocalDateTime expireDate) {
        return mockUserCoupon.mockUserCoupon(coupon, user, isUse, expireDate);
    }
}
