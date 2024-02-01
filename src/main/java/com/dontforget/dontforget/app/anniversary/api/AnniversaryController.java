package com.dontforget.dontforget.app.anniversary.api;

import com.dontforget.dontforget.app.anniversary.api.request.AnniversaryCreateRequest;
import com.dontforget.dontforget.app.anniversary.api.request.AnniversaryUpdateRequest;
import com.dontforget.dontforget.app.anniversary.api.response.AnniversaryDetailResponse;
import com.dontforget.dontforget.app.anniversary.api.response.AnniversaryListResponse;
import com.dontforget.dontforget.app.anniversary.application.AnniversaryApplication;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/anniversary")
public class AnniversaryController {

    private final AnniversaryApplication anniversaryApplication;

    @PostMapping
    public ResponseEntity<Void> createAnniversary(
        @RequestHeader("deviceId") final String deviceId,
        @RequestBody final AnniversaryCreateRequest request
    ) {
        final Long id = anniversaryApplication.create(request.toQuery(deviceId));

        return ResponseEntity.created(URI.create("/anniversary/" + id)).build();
    }

    @GetMapping("/{anniversaryId}")
    public ResponseEntity<AnniversaryDetailResponse> getAnniversary(@PathVariable final Long anniversaryId) {
        final AnniversaryDetailResponse anniversary = anniversaryApplication.getAnniversary(anniversaryId);

        return ResponseEntity.ok(anniversary);
    }

    @GetMapping
    public ResponseEntity<List<AnniversaryListResponse>> getAnniversaryList(
        @RequestHeader("deviceId") final String deviceId
    ) {
        final List<AnniversaryListResponse> anniversaryList = anniversaryApplication.getAnniversaryList(deviceId);

        return ResponseEntity.ok(anniversaryList);
    }

    @PutMapping("/{anniversaryId}")
    public ResponseEntity<Void> updateAnniversary(
        @PathVariable final Long anniversaryId,
        @RequestBody final AnniversaryUpdateRequest request
    ) {

        anniversaryApplication.updateAnniversary(request.toQuery(anniversaryId));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{anniversaryId}")
    public ResponseEntity<Void> deleteAnniversary(@PathVariable final Long anniversaryId) {
        anniversaryApplication.deleteAnniversary(anniversaryId);

        return ResponseEntity.noContent().build();
    }
}
