package com.dontforget.dontforget.infra.jpa.notice.repository;

import com.dontforget.dontforget.domain.notice.enums.NoticeStatus;
import com.dontforget.dontforget.infra.jpa.notice.NoticeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeEntityRepository extends JpaRepository<NoticeEntity, Long> {

    List<NoticeEntity> findAllByAnniversaryId(final Long anniversaryId);

    @Modifying
    @Query("delete from NoticeEntity n where n.anniversaryId = :anniversaryId")
    void deleteByAnniversaryId(final Long anniversaryId);


    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE NoticeEntity n SET n.noticeStatus =:noticeStatus WHERE n.id IN :noticeIds")
    void updateNoticeStatus(
        @Param("noticeStatus") final NoticeStatus noticeStatus,
        @Param("noticeIds") final List<Long> noticeIds
    );
}
