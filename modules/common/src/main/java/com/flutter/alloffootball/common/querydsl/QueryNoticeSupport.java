package com.flutter.alloffootball.common.querydsl;

import com.flutter.alloffootball.common.domain.admin.Notice;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.querydsl.suport.QueryDSLRepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.flutter.alloffootball.common.domain.admin.QNotice.notice;

@Repository
public class QueryNoticeSupport extends QueryDSLRepositorySupport {

    public QueryNoticeSupport() {
        super(Notice.class);
    }

    public Page<Notice> findAllBySearchNotice(Pageable pageable) {
        return applyPagination(notice, pageable,
            query -> query.selectFrom(notice)
                .orderBy(notice.id.desc()));
    }

}
