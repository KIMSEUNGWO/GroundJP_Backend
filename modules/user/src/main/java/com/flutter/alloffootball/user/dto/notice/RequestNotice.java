package com.flutter.alloffootball.user.dto.notice;

import com.flutter.alloffootball.common.domain.admin.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RequestNotice {

    private final String title;
    private final String content;
    private final LocalDateTime createDate;

    public RequestNotice(Notice notice) {
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createDate = notice.getCreateDate();
    }
}
