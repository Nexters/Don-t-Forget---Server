package com.dontforget.dontforget.config;

import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.notice.NoticeRepository;
import com.dontforget.dontforget.domain.notice.service.AlarmExecutor;
import com.dontforget.dontforget.infra.FCMExecutor;
import com.dontforget.dontforget.infra.jpa.AnniversaryRepositoryImpl;
import com.dontforget.dontforget.infra.jpa.NoticeRepositoryImpl;
import com.dontforget.dontforget.infra.jpa.anniversary.repository.AnniversaryEntityRepository;
import com.dontforget.dontforget.infra.jpa.notice.repository.NoticeEntityRepository;
import com.dontforget.dontforget.infra.mapper.AnniversaryMapper;
import com.dontforget.dontforget.infra.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RepositoryConfig {

    @Bean
    public AnniversaryRepository anniversaryRepository(
        AnniversaryEntityRepository anniversaryEntityRepository,
        AnniversaryMapper anniversaryMapper,
        NoticeRepository noticeRepository
    ) {
        return new AnniversaryRepositoryImpl(
            anniversaryEntityRepository,
            anniversaryMapper,
            noticeRepository
        );
    }

    @Bean
    public NoticeRepository noticeRepository(
        NoticeEntityRepository noticeEntityRepository,
        NoticeMapper noticeMapper
    ) {
        return new NoticeRepositoryImpl(
            noticeEntityRepository,
            noticeMapper
        );
    }

    @Bean
    @Profile("!test")
    public AlarmExecutor alarmExecutor(
        @Value("${firebase.key-path}") final String fcmKeyPath
    ) {
        return new FCMExecutor(fcmKeyPath);
    }
}
