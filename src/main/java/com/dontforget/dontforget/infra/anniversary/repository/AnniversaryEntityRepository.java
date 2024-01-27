package com.dontforget.dontforget.infra.anniversary.repository;

import com.dontforget.dontforget.infra.anniversary.AnniversaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnniversaryEntityRepository extends JpaRepository<AnniversaryEntity, Long> {

    Optional<AnniversaryEntity> findById(final Long anniversaryId);

    List<AnniversaryEntity> findAllByDeviceUuid(String deviceUuid);
}
