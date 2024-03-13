package com.dontforget.dontforget.domain.notice.query;

import com.dontforget.dontforget.domain.notice.enums.DeviceStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeDeviceRequest {

    private String token;
    private String deviceUuid;
    private DeviceStatus status;

    public NoticeDeviceRequest(String token, String deviceUuid, DeviceStatus status) {
        this.token = token;
        this.deviceUuid = deviceUuid;
        this.status = status;
    }
}
