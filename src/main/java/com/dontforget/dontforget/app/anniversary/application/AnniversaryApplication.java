package com.dontforget.dontforget.app.anniversary.application;

import com.dontforget.dontforget.app.anniversary.api.response.AnniversaryDetailResponse;
import com.dontforget.dontforget.domain.anniversary.query.CreateAnniversaryQuery;
import com.dontforget.dontforget.domain.anniversary.service.CreateAnniversary;
import com.dontforget.dontforget.domain.anniversary.service.ReadAnniversary;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnniversaryApplication {

    private final CreateAnniversary createAnniversary;
    private final ReadAnniversary readAnniversary;

    @Transactional
    public Long create(CreateAnniversaryQuery query) {
        return createAnniversary.create(query);
    }

    public AnniversaryDetailResponse getAnniversary(final Long anniversaryId) {
        return readAnniversary.getAnniversary(anniversaryId);
    }
}
