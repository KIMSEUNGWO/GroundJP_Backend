package com.flutter.alloffootball.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class BanWordException extends RuntimeException {

    private final List<String> words;

}
