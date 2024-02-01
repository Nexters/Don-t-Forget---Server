package com.dontforget.dontforget.app.notice.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FCMNoticeRequest {

    private String deviceUuid;
    private String title;
    private String body;

    public FCMNoticeRequest(String deviceUuid, String title, String body) {
        this.deviceUuid = deviceUuid;
        this.title = title;
        this.body = body;
    }
}
