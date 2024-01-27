package com.dontforget.dontforget.domain.anniversary;

import java.util.List;

public interface AnniversaryRepository {
    Anniversary findById(final Long id);

    Long save(final Anniversary anniversary);

    List<Anniversary> findByDeviceUuidOrderByRecentDate(final String deviceId);

    void update(final Anniversary anniversary);

    void delete(final Anniversary anniversary);
}
