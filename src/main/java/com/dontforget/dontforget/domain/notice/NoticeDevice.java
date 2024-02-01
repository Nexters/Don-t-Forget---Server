package com.dontforget.dontforget.domain.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeDevice {

    private Long id;
    private String deviceUuid;
    private DeviceStatus deviceStatus;

    public NoticeDevice(Long id, String deviceUuid, DeviceStatus deviceStatus) {
        this.id = id;
        this.deviceUuid = deviceUuid;
        this.deviceStatus = deviceStatus;
    }
}
