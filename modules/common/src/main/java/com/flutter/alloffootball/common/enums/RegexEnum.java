package com.flutter.alloffootball.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegexEnum {

    히라가나("\\u3040-\\u309F"),
    가타카나("\\u30A0-\\u30FF"),
    한자("\\u4E00-\\u9FFF"),
    일본기호("\\u3000-\\u303F"),
    전각문자("\\uFF00-\\uFFEF");

    private final String regex;
}
