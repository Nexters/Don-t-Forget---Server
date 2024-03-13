package com.dontforget.dontforget.config;

import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.notice.NoticeRepository;
import com.dontforget.dontforget.infra.jpa.AnniversaryRepositoryImpl;
import com.dontforget.dontforget.infra.jpa.NoticeRepositoryImpl;
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
    AnniversaryMapper anniversaryMapper() {
        return new AnniversaryMapper();
    }

    @Bean
    NoticeMapper noticeMapper() {
        return new NoticeMapper();
    }
}
