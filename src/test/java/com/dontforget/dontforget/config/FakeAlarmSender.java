package com.dontforget.dontforget.config;

import com.dontforget.dontforget.domain.notice.service.AlarmSender;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class FakeAlarmSender implements AlarmSender {

    @Override
    public String send(String deviceUuid, String title, String body) {
        return null;
    }
}
