package com.dontforget.dontforget.infra.notice.repository;

import com.dontforget.dontforget.infra.anniversary.AnniversaryEntity;
import com.dontforget.dontforget.infra.notice.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeEntityRepository extends JpaRepository<NoticeEntity, Long> {

}