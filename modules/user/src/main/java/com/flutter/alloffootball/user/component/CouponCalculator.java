package com.flutter.alloffootball.user.component;

import org.springframework.stereotype.Component;

@Component
public class CouponCalculator {

    public int discount(int price, int per) {
        return (int) (price * (Math.max(0, per) / 100.0));
    }

    public int total(int price, int per) {
        return price - discount(price, per);
    }


}