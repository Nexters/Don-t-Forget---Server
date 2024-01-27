package com.dontforget.dontforget.config;

import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.infra.anniversary.repository.AnniversaryEntityRepository;
import com.dontforget.dontforget.infra.anniversary.repository.AnniversaryRepositoryImpl;
import com.dontforget.dontforget.infra.mapper.AnniversaryMapper;
import com.dontforget.dontforget.infra.mapper.NoticeMapper;
import com.dontforget.dontforget.infra.notice.repository.NoticeEntityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

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
}
