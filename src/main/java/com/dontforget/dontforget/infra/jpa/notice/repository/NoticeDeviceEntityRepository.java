package com.dontforget.dontforget.infra.jpa.notice.repository;

import com.dontforget.dontforget.infra.jpa.notice.NoticeDeviceEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeDeviceEntityRepository extends JpaRepository<NoticeDeviceEntity, Long> {

    Optional<NoticeDeviceEntity> findNoticeDeviceEntityByDeviceUuid(String deviceUuid);
}
