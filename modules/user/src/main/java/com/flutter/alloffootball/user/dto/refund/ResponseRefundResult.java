package com.flutter.alloffootball.user.dto.refund;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseRefundResult {

    private final int payAmount;
    private final int refundAmount;
}
