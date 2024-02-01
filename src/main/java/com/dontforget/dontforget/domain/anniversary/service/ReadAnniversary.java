package com.dontforget.dontforget.domain.anniversary.service;

import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ReadAnniversary {
    private final AnniversaryRepository anniversaryRepository;

    public Anniversary getAnniversary(final Long anniversaryId) {
        return anniversaryRepository.findById(anniversaryId);
    }

    public List<Anniversary> getAnniversaryList(final String deviceId) {
        return anniversaryRepository.findByDeviceUuidOrderByRecentDate(deviceId);
    }
}
