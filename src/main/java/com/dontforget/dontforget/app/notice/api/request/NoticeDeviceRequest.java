package com.dontforget.dontforget.app.notice.api.request;

import com.dontforget.dontforget.domain.notice.DeviceStatus;
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
