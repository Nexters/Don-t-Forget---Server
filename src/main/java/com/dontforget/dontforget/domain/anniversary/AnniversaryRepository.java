package com.dontforget.dontforget.domain.anniversary;

import com.dontforget.dontforget.infra.jpa.anniversary.AnniversaryEntity;
import java.util.List;

public interface AnniversaryRepository {
    Anniversary findById(final Long id);

    Long save(final Anniversary anniversary);

    List<AnniversaryEntity> findByDeviceUuidOrderByRecentDate(final String deviceId);
}
