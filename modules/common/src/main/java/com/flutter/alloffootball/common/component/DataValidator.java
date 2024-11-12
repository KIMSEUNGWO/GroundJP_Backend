package com.flutter.alloffootball.common.component;

import com.flutter.alloffootball.common.dto.InvalidData;
import com.flutter.alloffootball.common.enums.RegexEnum;
import com.flutter.alloffootball.common.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class DataValidator {

    private final Pattern p;

    public DataValidator() {
        String add = Arrays.stream(RegexEnum.values()).map(RegexEnum::getRegex).collect(Collectors.joining());
        String regex = "^[가-힣0-9a-zA-Z" + add + "]+$";
        p = Pattern.compile(regex);
    }

    public void validNickname(String nickname) {

        if (nickname == null || nickname.isEmpty()) {
            return;
        }

        if (nickname.contains(" ")) {
            throw new InvalidDataException(new InvalidData("nickname", "띄어쓰기는 사용할 수 없습니다."));
        }

        if (nickname.length() < 2 || nickname.length() > 8) {
            throw new InvalidDataException(new InvalidData("nickname", "닉네임은 2 ~ 8자 까지 가능합니다."));
        }

        if (nickname.toLowerCase().contains("null")) {
            throw new InvalidDataException(new InvalidData("nickname", "null 을 포함한 문자열은 사용할 수 없습니다."));
        }

        Matcher m = p.matcher(nickname);
        if (!m.matches()) {
            throw new InvalidDataException(new InvalidData("nickname", "사용할 수 없는 문자입니다."));
        }


    }
}
