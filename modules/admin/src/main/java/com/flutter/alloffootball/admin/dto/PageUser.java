package com.flutter.alloffootball.admin.dto;

import com.flutter.alloffootball.admin.dto.user.RequestSearchUser;
import com.flutter.alloffootball.common.dto.PageDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageUser<T> extends PageDto<T> {

    private final String word;

    public PageUser(Page<T> page, RequestSearchUser data) {
        super(page);
        this.word = data.getWord();
    }
}
