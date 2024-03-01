package com.dontforget.dontforget.infra.mapper;

import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeDevice;
import com.dontforget.dontforget.domain.notice.NoticeStatus;
import com.dontforget.dontforget.infra.jpa.notice.NoticeDeviceEntity;
import com.dontforget.dontforget.infra.jpa.notice.NoticeEntity;
import org.springframework.stereotype.Component;

@Component
public class NoticeMapper {

    public NoticeEntity toEntity(final Notice notice, final Long anniversaryId) {
        return new NoticeEntity(
            notice.getId(),
            anniversaryId,
            notice.getNoticeType(),
            NoticeStatus.WAITING_SEND
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

    public NoticeEntity toEntity(final Notice notice) {
        return new NoticeEntity(
            notice.getId(),
            notice.getAnniversaryId(),
            notice.getNoticeType(),
            NoticeStatus.WAITING_SEND
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

    public NoticeDeviceEntity toEntity(final NoticeDevice device, final Long id) {
        return new NoticeDeviceEntity(
            id,
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
