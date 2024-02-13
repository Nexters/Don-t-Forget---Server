package com.dontforget.dontforget.domain.notice.service;

import com.dontforget.dontforget.app.notice.api.request.NoticeDeviceRequest;
import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.domain.notice.NoticeDevice;
import com.dontforget.dontforget.domain.notice.NoticeDeviceRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class CreateNoticeDevice {

    private final NoticeDeviceRepository repository;

    public Long create(final NoticeDeviceRequest request) {
        final NoticeDevice noticeDevice = NoticeDevice.create(
            request.getDeviceUuid(),
            request.getToken(),
            request.getStatus()
        );
        return repository.save(noticeDevice);
    }
}
