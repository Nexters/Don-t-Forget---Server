package com.dontforget.dontforget.infra;

import com.dontforget.dontforget.domain.notice.service.AlarmExecutor;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public class FCMExecutor implements AlarmExecutor {

    private final String fcmKeyPath;

    public FCMExecutor(String fcmKeyPath) {
        this.fcmKeyPath = fcmKeyPath;
    }

    @PostConstruct
    public void initialize() {
        try {
            final InputStream refreshToken = new ClassPathResource(fcmKeyPath).getInputStream();

            final FirebaseOptions options = FirebaseOptions
                .builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();

            FirebaseApp.initializeApp(options);
            log.info("Fcm Setting Completed");

        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
