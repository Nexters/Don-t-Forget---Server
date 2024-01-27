package com.dontforget.dontforget.domain.anniversary.service;

import com.dontforget.dontforget.app.anniversary.api.response.AnniversaryDetailResponse;
import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
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
                .map(it-> it.getNoticeType().toString()).toList(),
            anniversary.getContent(),
            anniversary.getDeviceUuid()
        );
    }
}
