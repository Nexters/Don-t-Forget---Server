package com.dontforget.dontforget.infra.mapper;

import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeDevice;
import com.dontforget.dontforget.infra.jpa.notice.NoticeDeviceEntity;
import com.dontforget.dontforget.infra.jpa.notice.NoticeEntity;
import org.springframework.stereotype.Component;

@Component
public class NoticeMapper {

    public NoticeEntity toEntity(final Notice notice, final Long anniversaryId) {
        return new NoticeEntity(
            notice.getId(),
            anniversaryId,
            notice.getNoticeType()
        );
    }

    public Notice toDomain(final NoticeEntity noticeEntity) {
        return new Notice(
            noticeEntity.getId(),
            noticeEntity.getAnniversaryId(),
            noticeEntity.getNoticeType()
        );
    }

    public NoticeEntity toEntity(final Notice notice) {
        return new NoticeEntity(
            notice.getId(),
            notice.getAnniversaryId(),
            notice.getNoticeType()
        );
    }

    public NoticeDeviceEntity toEntity(final NoticeDevice device) {
        return new NoticeDeviceEntity(
            device.getId(),
            device.getDeviceUuid(),
            device.getFcmToken(),
            device.getDeviceStatus()
        );
    }

    public NoticeDevice toDomain(final NoticeDeviceEntity device) {
        return new NoticeDevice(
            device.getId(),
            device.getDeviceUuid(),
            device.getFcmToken(),
            device.getDeviceStatus()
        );
    }
}
