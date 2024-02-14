package com.dontforget.dontforget.infra.config;

import com.dontforget.dontforget.domain.notice.AlarmExecutor;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FCMExecutor implements AlarmExecutor {

    @Value("${firebase.key-path}")
    private String fcmKeyPath;

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
