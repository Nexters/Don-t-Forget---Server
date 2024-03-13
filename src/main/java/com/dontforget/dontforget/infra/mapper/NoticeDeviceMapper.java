package com.dontforget.dontforget.infra.mapper;

import com.dontforget.dontforget.domain.notice.NoticeDevice;
import com.dontforget.dontforget.infra.jpa.notice.NoticeDeviceEntity;
import org.springframework.stereotype.Component;

@Component
public class NoticeDeviceMapper {
    public NoticeDeviceEntity toEntity(final NoticeDevice device, final Long id) {
        return new NoticeDeviceEntity(
            id,
            device.getDeviceUuid(),
            device.getFcmToken(),
            device.getDeviceStatus()
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
