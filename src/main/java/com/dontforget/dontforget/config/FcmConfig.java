package com.dontforget.dontforget.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("!test")
public class FcmConfig {

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
