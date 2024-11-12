package com.flutter.alloffootball.admin.dto.notice;


import com.flutter.alloffootball.common.domain.admin.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseNoticeListView {

    private final Long noticeId;
    private final String title;
    private final LocalDateTime createDate;

    public ResponseNoticeListView(Notice notice) {
        this.noticeId = notice.getId();
        this.title = notice.getTitle();
        this.createDate = notice.getCreateDate();
    }
}
