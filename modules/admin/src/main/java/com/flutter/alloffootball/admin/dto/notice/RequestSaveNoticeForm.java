package com.flutter.alloffootball.admin.dto.notice;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestSaveNoticeForm {

    @NotNull
    private String title;
    @NotNull
    private String content;
}
