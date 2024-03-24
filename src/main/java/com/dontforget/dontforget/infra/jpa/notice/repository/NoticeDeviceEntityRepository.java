package com.dontforget.dontforget.infra.jpa.notice.repository;

import com.dontforget.dontforget.infra.jpa.notice.NoticeDeviceEntity;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface NoticeDeviceEntityRepository extends JpaRepository<NoticeDeviceEntity, Long> {

    Optional<NoticeDeviceEntity> findNoticeDeviceEntityByDeviceUuid(String deviceUuid);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT n FROM NoticeDeviceEntity n WHERE n.deviceUuid = :deviceUuid")
    Optional<NoticeDeviceEntity> findNoticeDeviceEntityByDeviceUuidForUpdate(String deviceUuid);
}
