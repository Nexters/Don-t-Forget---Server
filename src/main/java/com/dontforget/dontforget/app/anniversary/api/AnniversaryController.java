package com.dontforget.dontforget.app.anniversary.api;

import com.dontforget.dontforget.app.anniversary.api.request.AnniversaryCreateRequest;
import com.dontforget.dontforget.app.anniversary.api.request.AnniversaryUpdateRequest;
import com.dontforget.dontforget.app.anniversary.api.response.AnniversaryDetailResponse;
import com.dontforget.dontforget.app.anniversary.api.response.AnniversaryListResponse;
import com.dontforget.dontforget.app.anniversary.application.AnniversaryApplication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "anniversary", description = "기념일 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/anniversary")
public class AnniversaryController {

    private final AnniversaryApplication anniversaryApplication;

    @Operation(summary = "create anniversary", description = "기념일 생성하기")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATE"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping
    public ResponseEntity<Long> createAnniversary(
        @RequestHeader("deviceId") final String deviceId,
        @RequestBody final AnniversaryCreateRequest request
    ) {
        final Long id = anniversaryApplication.create(request.toQuery(deviceId));

        return ResponseEntity.created(URI.create("/api/anniversary/" + id)).body(id);
    }

    @Operation(summary = "get anniversary", description = "기념일 단건 조회하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AnniversaryDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/{anniversaryId}")
    public ResponseEntity<AnniversaryDetailResponse> getAnniversary(@PathVariable final Long anniversaryId) {
        final AnniversaryDetailResponse anniversary = anniversaryApplication.getAnniversary(anniversaryId);

        return ResponseEntity.ok(anniversary);
    }

    @Operation(summary = "get anniversary list", description = "기념일 목록 조회하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AnniversaryDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping
    public ResponseEntity<List<AnniversaryListResponse>> getAnniversaryList(
        @RequestHeader("deviceId") final String deviceId
    ) {
        final List<AnniversaryListResponse> anniversaryList = anniversaryApplication.getAnniversaryList(deviceId);

        return ResponseEntity.ok(anniversaryList);
    }

    @Operation(summary = "edit anniversary", description = "기념일 수정하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/{anniversaryId}")
    public ResponseEntity<Void> updateAnniversary(
        @PathVariable final Long anniversaryId,
        @RequestBody final AnniversaryUpdateRequest request
    ) {

        anniversaryApplication.updateAnniversary(request.toQuery(anniversaryId));

        return ResponseEntity.ok().build();
    }
    @Operation(summary = "delete anniversary", description = "기념일 삭제하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @DeleteMapping("/{anniversaryId}")
    public ResponseEntity<Void> deleteAnniversary(@PathVariable final Long anniversaryId) {
        anniversaryApplication.deleteAnniversary(anniversaryId);

        return ResponseEntity.noContent().build();
    }
}
