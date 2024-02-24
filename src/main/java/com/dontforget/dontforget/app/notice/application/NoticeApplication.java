package com.dontforget.dontforget.app.notice.application;

import com.dontforget.dontforget.app.notice.api.request.FCMNoticeRequest;
import com.dontforget.dontforget.domain.notice.NoticeDeviceRequest;
import com.dontforget.dontforget.domain.notice.NoticeDevice;
import com.dontforget.dontforget.domain.notice.NoticeDeviceRepository;
import com.dontforget.dontforget.domain.notice.service.CreateNoticeDevice;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeApplication {

    private final NoticeDeviceRepository noticeDeviceRepository;
    private final CreateNoticeDevice createNoticeDevice;

    @Transactional(readOnly = true)
    public String sendNoticeByToken(FCMNoticeRequest request) {
        NoticeDevice user = noticeDeviceRepository.findByUuid(request.getDeviceUuid());

        Message message = buildFcmMessage(request, user.getFcmToken());
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM-send-" + response);
        } catch (FirebaseMessagingException e) {
            log.info("FCM-except-" + e.getMessage());
            return "알림 실패";
        }
        return "알림 전송";
    }

    private Message buildFcmMessage(FCMNoticeRequest request, String token) {
        return Message.builder()
            .setToken(token)
            .setNotification(
                Notification.builder()
                    .setTitle(request.getTitle())
                    .setBody(request.getBody())
                    .build()
            )
            .setAndroidConfig(buildAndroidConfig(request))
            .setApnsConfig(buildApnsConfig())
            .build();
    }

    private AndroidConfig buildAndroidConfig(FCMNoticeRequest request) {
        return AndroidConfig.builder()
            .setNotification(
                AndroidNotification.builder()
                    .setTitle(request.getTitle())
                    .setBody(request.getBody())
                    .setClickAction("push_click")
                    .build()
            )
            .build();
    }

    private ApnsConfig buildApnsConfig() {
        return ApnsConfig.builder()
            .setAps(Aps.builder()
                .setCategory("push_click")
                .build())
            .build();
    }

    @Transactional
    public Long upsert(final NoticeDeviceRequest request) {
        return createNoticeDevice.upsert(request);
    }
}
