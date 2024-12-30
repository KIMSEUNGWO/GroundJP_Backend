package com.flutter.alloffootball.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleAdmin {

    SUPER_ADMIN("ROLE_SUPER_ADMIN","최고관리자"),
    MATCH_ADMIN("ROLE_MATCH_ADMIN", "경기담당자"),
    HR_ADMIN("ROLE_HR_ADMIN", "인사담당자"),
    INQUIRE_ADMIN("ROLE_INQUIRE_ADMIN", "문의담당자");

    private final String roleName;
    private final String ko;
}
