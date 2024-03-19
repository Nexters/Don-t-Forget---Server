package com.dontforget.dontforget.domain.notice.service;

import com.dontforget.dontforget.domain.notice.NoticeTarget;
import java.util.List;

public interface AlarmSender {
    String send(String deviceUuid, String title, String body);

    void sendNotifications(List<NoticeTarget> expectedNotices);
}
