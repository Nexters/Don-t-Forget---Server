package com.dontforget.dontforget.app.notice.application;

import com.dontforget.dontforget.app.notice.api.request.FCMNoticeRequest;
import com.dontforget.dontforget.domain.notice.NoticeDeviceRequest;
import com.dontforget.dontforget.domain.notice.service.AlarmSender;
import com.dontforget.dontforget.domain.notice.service.CreateNoticeDevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeApplication {

    private final CreateNoticeDevice createNoticeDevice;
    private final AlarmSender alarmSender;

    @Transactional(readOnly = true)
    public String sendNoticeByToken(FCMNoticeRequest request) {
        return alarmSender.send(request.getDeviceUuid(), request.getTitle(), request.getBody());
    }

    @Transactional
    public Long upsert(final NoticeDeviceRequest request) {
        return createNoticeDevice.upsert(request);
    }
}
