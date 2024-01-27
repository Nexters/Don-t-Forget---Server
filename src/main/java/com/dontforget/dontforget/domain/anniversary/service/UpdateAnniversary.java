package com.dontforget.dontforget.domain.anniversary.service;

import com.dontforget.dontforget.app.anniversary.api.request.AnniversaryUpdateRequest;
import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class UpdateAnniversary {

    private final AnniversaryRepository anniversaryRepository;
    private final CalendarCalculator calendarCalculator;

    public void updateAnniversary(final Long anniversaryId, final AnniversaryUpdateRequest request) {
        Anniversary anniversary = anniversaryRepository.findById(anniversaryId);
        anniversary.update(
            request.title(), request.date(),
            request.type(), request.alarmSchedule(),
            request.content(),
            calendarCalculator
        );
        anniversaryRepository.update(anniversary);
    }
}
