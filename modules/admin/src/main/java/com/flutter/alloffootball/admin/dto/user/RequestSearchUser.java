package com.flutter.alloffootball.admin.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestSearchUser {

    private String word = "";
    private int page = 1;
}
