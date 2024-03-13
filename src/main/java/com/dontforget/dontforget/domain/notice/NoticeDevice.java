package com.dontforget.dontforget.domain.notice;

import com.dontforget.dontforget.domain.notice.enums.DeviceStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeDevice {

    private Long id;
    private String deviceUuid;
    private String fcmToken;
    private DeviceStatus deviceStatus;

    public NoticeDevice(Long id, String deviceUuid, String fcmToken, DeviceStatus deviceStatus) {
        this.id = id;
        this.deviceUuid = deviceUuid;
        this.fcmToken = fcmToken;
        this.deviceStatus = deviceStatus;
    }

    public static NoticeDevice create(
        String deviceUuid,
        String fcmToken,
        DeviceStatus status
    ) {
        return new NoticeDevice(
            null,
            deviceUuid,
            fcmToken,
            status
        );
    }
}
