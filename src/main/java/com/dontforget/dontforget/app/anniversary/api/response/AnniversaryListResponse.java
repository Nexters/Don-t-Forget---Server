package com.dontforget.dontforget.app.anniversary.api.response;

import com.dontforget.dontforget.domain.anniversary.Anniversary;
import java.time.LocalDate;

public record AnniversaryListResponse(Long anniversaryId, String title, LocalDate lunarDate, LocalDate solarDate) {

    public static AnniversaryListResponse from(final Anniversary anniversary) {
        return new AnniversaryListResponse(
            anniversary.getId(),
            anniversary.getTitle(),
            anniversary.getLunarDate(),
            anniversary.getSolarDate()
        );
    }
}
