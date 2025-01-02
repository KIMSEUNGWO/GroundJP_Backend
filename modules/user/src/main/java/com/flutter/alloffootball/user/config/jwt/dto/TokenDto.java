package com.flutter.alloffootball.user.config.jwt.dto;

import java.time.Instant;

public record TokenDto(String token, Instant expires) {
}
