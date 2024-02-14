package com.dontforget.dontforget.domain.notice.exception;

public class NotFoundNoticeDeviceException extends RuntimeException {

    public NotFoundNoticeDeviceException(String deviceUuid) {
        super(String.format("id가 %s 인 디바이스 정보가 존재하지 않습니다.", deviceUuid));
    }
}
