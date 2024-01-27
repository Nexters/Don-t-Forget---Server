package com.dontforget.dontforget.app.anniversary.application;

import com.dontforget.dontforget.app.anniversary.api.request.AnniversaryUpdateRequest;
import com.dontforget.dontforget.app.anniversary.api.response.AnniversaryDetailResponse;
import com.dontforget.dontforget.app.anniversary.api.response.AnniversaryListResponse;
import com.dontforget.dontforget.domain.anniversary.query.CreateAnniversaryQuery;
import com.dontforget.dontforget.domain.anniversary.service.CreateAnniversary;
import com.dontforget.dontforget.domain.anniversary.service.ReadAnniversary;
import com.dontforget.dontforget.domain.anniversary.service.UpdateAnniversary;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnniversaryApplication {

    private final CreateAnniversary createAnniversary;
    private final ReadAnniversary readAnniversary;
    private final UpdateAnniversary updateAnniversary;

    @Transactional
    public Long create(CreateAnniversaryQuery query) {
        return createAnniversary.create(query);
    }

    public AnniversaryDetailResponse getAnniversary(final Long anniversaryId) {
        return readAnniversary.getAnniversary(anniversaryId);
    }

    public List<AnniversaryListResponse> getAnniversaryList(final String deviceId) {
        return readAnniversary.getAnniversaryList(deviceId);
    }

    @Transactional
    public void updateAnniversary(final Long anniversaryId, final AnniversaryUpdateRequest request) {
        updateAnniversary.updateAnniversary(anniversaryId, request);
    }
}
