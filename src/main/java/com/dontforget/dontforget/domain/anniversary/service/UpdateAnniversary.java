package com.dontforget.dontforget.domain.anniversary.service;

import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.query.UpdateAnniversaryQuery;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class UpdateAnniversary {

    private final AnniversaryRepository anniversaryRepository;
    private final CalendarCalculator calendarCalculator;

    public void updateAnniversary(final UpdateAnniversaryQuery request) {
        final Anniversary anniversary = anniversaryRepository.findById(request.anniversaryId());
        anniversary.update(
            request.title(),
            request.date(),
            request.calendarType(),
            request.alarmSchedule(),
            request.content(),
            calendarCalculator
        );
        anniversaryRepository.update(anniversary);
    }
}
