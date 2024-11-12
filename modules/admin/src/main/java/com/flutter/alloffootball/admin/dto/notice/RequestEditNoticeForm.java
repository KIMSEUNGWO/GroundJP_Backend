package com.flutter.alloffootball.admin.dto.notice;

import com.flutter.alloffootball.common.domain.admin.Notice;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestEditNoticeForm {

    @NotNull
    private String title;
    @NotNull
    private String content;

    public RequestEditNoticeForm(Notice notice) {
        this.title = notice.getTitle();
        this.content = notice.getContent();
    }
}
