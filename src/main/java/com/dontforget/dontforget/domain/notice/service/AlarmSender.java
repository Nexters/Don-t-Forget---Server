package com.dontforget.dontforget.domain.notice.service;

import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.domain.notice.NoticeDevice;
import com.dontforget.dontforget.domain.notice.NoticeDeviceRepository;
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

@Slf4j
@DomainService
@RequiredArgsConstructor
public class AlarmSender {

    public static final String PUSH_CLICK = "push_click";

    private final NoticeDeviceRepository noticeDeviceRepository;

    public String send(String deviceUuid, String title, String body) {
        NoticeDevice user = noticeDeviceRepository.findByUuid(deviceUuid);

        Message message = buildFcmMessage(title, body, user.getFcmToken());
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM-send-" + response);
        } catch (FirebaseMessagingException e) {
            log.info("FCM-except-" + e.getMessage());
            return "알림 실패";
        }
        return "알림 전송";
    }

    private Message buildFcmMessage(String title, String body, String token) {
        return Message.builder()
            .setToken(token)
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build()
            )
            .setAndroidConfig(buildAndroidConfig(title, body))
            .setApnsConfig(buildApnsConfig())
            .build();
    }

    private AndroidConfig buildAndroidConfig(String title, String body) {
        return AndroidConfig.builder()
            .setNotification(
                AndroidNotification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .setClickAction(PUSH_CLICK)
                    .build()
            )
            .build();
    }

    private ApnsConfig buildApnsConfig() {
        return ApnsConfig.builder()
            .setAps(Aps.builder()
                .setCategory(PUSH_CLICK)
                .build())
            .build();
    }
}
