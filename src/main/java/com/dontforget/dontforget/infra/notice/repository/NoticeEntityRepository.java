package com.dontforget.dontforget.infra.notice.repository;

import com.dontforget.dontforget.infra.notice.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeEntityRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findAllByAnniversaryId(final Long anniversaryId);
}
