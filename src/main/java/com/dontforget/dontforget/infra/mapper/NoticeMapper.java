package com.dontforget.dontforget.infra.mapper;

import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.infra.jpa.notice.NoticeEntity;
import org.springframework.stereotype.Component;

@Component
public class NoticeMapper {

    public NoticeEntity toEntity(final Notice notice, final Long anniversaryId) {
        return new NoticeEntity(
            notice.getId(),
            anniversaryId,
            notice.getNoticeType(),
            notice.getNoticeStatus()
        );
    }

    public Notice toDomain(final NoticeEntity noticeEntity) {
        return new Notice(
            noticeEntity.getId(),
            noticeEntity.getAnniversaryId(),
            noticeEntity.getNoticeType(),
            noticeEntity.getNoticeStatus()
        );
    }
}
