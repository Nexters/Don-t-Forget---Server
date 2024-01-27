package com.dontforget.dontforget.infra.jpa.anniversary.repository;

import com.dontforget.dontforget.infra.jpa.anniversary.AnniversaryEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnniversaryEntityRepository extends JpaRepository<AnniversaryEntity, Long> {

    Optional<AnniversaryEntity> findById(final Long anniversaryId);


    @Query(value = "SELECT * FROM anniversary WHERE device_uuid = ?1 ORDER BY solar_date - now() ASC", nativeQuery = true)
    List<AnniversaryEntity> findByDeviceUuidOrderByRecentDate(final String deviceId);
}
