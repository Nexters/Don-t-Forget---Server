package com.dontforget.dontforget.domain.anniversary;

import java.util.List;

public interface AnniversaryRepository {

    List<Anniversary> findAll();

    Anniversary findById(final Long id);


    Long save(final Anniversary anniversary);

    void saveAll(final List<Anniversary> anniversaryList);

    List<Anniversary> findByDeviceUuidOrderByRecentDate(final String deviceId);

    void update(final Anniversary anniversary);

    void delete(final Anniversary anniversary);
}
