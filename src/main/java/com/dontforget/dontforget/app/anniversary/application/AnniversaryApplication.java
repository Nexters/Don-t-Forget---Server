package com.dontforget.dontforget.app.anniversary.application;

import com.dontforget.dontforget.app.anniversary.api.response.AnniversaryDetailResponse;
import com.dontforget.dontforget.app.anniversary.api.response.AnniversaryListResponse;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.query.CreateAnniversaryQuery;
import com.dontforget.dontforget.domain.anniversary.query.UpdateAnniversaryQuery;
import com.dontforget.dontforget.domain.anniversary.service.CreateAnniversary;
import com.dontforget.dontforget.domain.anniversary.service.DeleteAnniversary;
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
    private final DeleteAnniversary deleteAnniversary;

    @Transactional
    public Long create(CreateAnniversaryQuery query) {
        return createAnniversary.create(query);
    }

    public AnniversaryDetailResponse getAnniversary(final Long anniversaryId) {
        final Anniversary anniversary = readAnniversary.getAnniversary(anniversaryId);

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
        return readAnniversary.getAnniversaryList(deviceId).stream()
            .map(it -> new AnniversaryListResponse(
                it.getId(),
                it.getTitle(),
                it.getLunarDate(),
                it.getSolarDate()
            ))
            .toList();
    }

    @Transactional
    public void updateAnniversary(final UpdateAnniversaryQuery request) {
        updateAnniversary.updateAnniversary(request);
    }

    @Transactional
    public void deleteAnniversary(final Long anniversaryId) {
        deleteAnniversary.deleteAnniversary(anniversaryId);
    }
}
