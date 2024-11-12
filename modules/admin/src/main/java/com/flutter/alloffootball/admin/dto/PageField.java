package com.flutter.alloffootball.admin.dto;

import com.flutter.alloffootball.admin.dto.field.RequestSearchField;
import com.flutter.alloffootball.common.dto.PageDto;
import com.flutter.alloffootball.common.enums.region.Region;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageField<T> extends PageDto<T> {

    private final String word;
    private final Region region;

    public PageField(Page<T> page, RequestSearchField data) {
        super(page);
        this.word = data.getWord();
        this.region = data.getRegion();
    }
}
