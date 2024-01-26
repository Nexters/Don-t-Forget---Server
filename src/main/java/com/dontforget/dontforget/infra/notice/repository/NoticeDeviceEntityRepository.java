package com.dontforget.dontforget.infra.notice.repository;

import com.dontforget.dontforget.infra.notice.NoticeDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeDeviceEntityRepository extends JpaRepository<NoticeDeviceEntity, Long> {

}