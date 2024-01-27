package com.dontforget.dontforget.domain.anniversary.service;

import com.dontforget.dontforget.app.anniversary.api.response.AnniversaryDetailResponse;
import com.dontforget.dontforget.app.anniversary.api.response.AnniversaryListResponse;
import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.infra.jpa.anniversary.AnniversaryEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ReadAnniversary {
    private final AnniversaryRepository anniversaryRepository;

    public AnniversaryDetailResponse getAnniversary(final Long anniversaryId) {
        final Anniversary anniversary = anniversaryRepository.findById(anniversaryId);

        return new AnniversaryDetailResponse(
            anniversary.getId(),
            anniversary.getTitle(),
            anniversary.getLunarDate(),
            anniversary.getSolarDate(),
            anniversary.getNotices()
                .stream()
                .map(it -> it.getNoticeType().toString()).toList(),
            anniversary.getContent(),
            anniversary.getDeviceUuid()
        );
    }

    public List<AnniversaryListResponse> getAnniversaryList(final String deviceId) {
        final List<AnniversaryEntity> anniversaries = anniversaryRepository.findByDeviceUuidOrderByRecentDate(
            deviceId);

        return anniversaries.stream()
            .map(
                it -> new AnniversaryListResponse(
                    it.getId(),
                    it.getTitle(),
                    it.getLunarDate(),
                    it.getSolarDate())
            ).toList();
    }
}
