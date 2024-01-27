package com.dontforget.dontforget.infra.jpa.notice.repository;

import com.dontforget.dontforget.infra.jpa.notice.NoticeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoticeEntityRepository extends JpaRepository<NoticeEntity, Long> {

    List<NoticeEntity> findAllByAnniversaryId(final Long anniversaryId);

    @Modifying
    @Query("delete from NoticeEntity n where n.anniversaryId = :anniversaryId")
    void deleteByAnniversaryId(final Long anniversaryId);
}
