package com.dontforget.dontforget.infra.anniversary.repository;

import com.dontforget.dontforget.infra.anniversary.AnniversaryEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnniversaryEntityRepository extends JpaRepository<AnniversaryEntity, Long> {

  Optional<AnniversaryEntity> findById(Long id);
  List<AnniversaryEntity> findAllByDeviceUuid(String deviceUuid);

}