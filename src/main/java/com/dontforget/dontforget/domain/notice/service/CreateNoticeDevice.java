package com.dontforget.dontforget.domain.notice.service;

import com.dontforget.dontforget.domain.notice.query.NoticeDeviceRequest;
import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.domain.notice.NoticeDevice;
import com.dontforget.dontforget.domain.notice.NoticeDeviceRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class CreateNoticeDevice {

    private final NoticeDeviceRepository repository;

    public Long upsert(final NoticeDeviceRequest request) {
        final NoticeDevice noticeDevice = NoticeDevice.create(
            request.getDeviceUuid(),
            request.getToken(),
            request.getStatus()
        );
        return repository.upsert(noticeDevice);
    }
}
