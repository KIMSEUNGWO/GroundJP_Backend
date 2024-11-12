package com.flutter.alloffootball.common.exception;

import lombok.Getter;

@Getter
public class OrderException extends CustomRuntimeException{

    private final OrderError error;

    public OrderException(OrderError error) {
        super(error);
        this.error = error;
    }
}
