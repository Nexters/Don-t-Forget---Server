package com.dontforget.dontforget.config;

import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.notice.AlarmExecutor;
import com.dontforget.dontforget.infra.jpa.AnniversaryRepositoryImpl;
import com.dontforget.dontforget.infra.jpa.anniversary.repository.AnniversaryEntityRepository;
import com.dontforget.dontforget.infra.jpa.notice.repository.NoticeEntityRepository;
import com.dontforget.dontforget.infra.mapper.AnniversaryMapper;
import com.dontforget.dontforget.infra.mapper.NoticeMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RepositoryTestConfig {

    @Bean
    public AnniversaryRepository anniversaryRepository(
        AnniversaryEntityRepository anniversaryEntityRepository,
        NoticeEntityRepository noticeEntityRepository,
        AnniversaryMapper anniversaryMapper,
        NoticeMapper noticeMapper
    ) {
        return new AnniversaryRepositoryImpl(
            anniversaryEntityRepository,
            noticeEntityRepository,
            anniversaryMapper,
            noticeMapper
        );
    }

    @Bean
    AnniversaryMapper anniversaryMapper() {
        return new AnniversaryMapper();
    }

    @Bean
    NoticeMapper noticeMapper() {
        return new NoticeMapper();
    }

    @Bean
    AlarmExecutor alarmExecutor() {
        return new FakeFcmExecutor();
    }
}
