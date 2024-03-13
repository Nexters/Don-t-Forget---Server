package com.dontforget.dontforget.domain.notice.service;

public interface AlarmSender {
    String send(String deviceUuid, String title, String body);
}
