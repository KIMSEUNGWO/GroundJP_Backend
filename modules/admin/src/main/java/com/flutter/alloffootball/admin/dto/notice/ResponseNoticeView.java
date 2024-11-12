package com.flutter.alloffootball.admin.dto.notice;

import com.flutter.alloffootball.common.domain.admin.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseNoticeView {

    private final String title;
    private final String content;
    private final LocalDateTime createDate;

    public ResponseNoticeView(Notice notice) {
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createDate = notice.getCreateDate();
    }
}
