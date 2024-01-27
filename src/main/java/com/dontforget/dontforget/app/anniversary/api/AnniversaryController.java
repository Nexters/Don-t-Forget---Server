package com.dontforget.dontforget.app.anniversary.api;

import com.dontforget.dontforget.app.anniversary.api.request.AnniversaryCreateRequest;
import com.dontforget.dontforget.app.anniversary.application.AnniversaryApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/anniversary/")
public class AnniversaryController {

    private final AnniversaryApplication anniversaryApplication;

    @PostMapping
    public ResponseEntity<Void> createAnniversary(
            @RequestHeader("deviceId") final String deviceId,
            @RequestBody final AnniversaryCreateRequest request
    ) {
        final Long id = anniversaryApplication.create(request.toQuery(deviceId));

        return ResponseEntity.created(URI.create("anniversary/" + id)).build();
    }
}
