package com.dontforget.dontforget.app.anniversary.api.response;

import java.time.LocalDate;

public record AnniversaryListResponse(Long anniversaryId, String title, LocalDate lunarDate, LocalDate solarDate) {
}
