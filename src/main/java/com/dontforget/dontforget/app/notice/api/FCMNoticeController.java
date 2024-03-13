package com.dontforget.dontforget.app.notice.api;

import com.dontforget.dontforget.app.notice.api.request.FCMNoticeRequest;
import com.dontforget.dontforget.domain.notice.query.NoticeDeviceRequest;
import com.dontforget.dontforget.app.notice.application.NoticeApplication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "notice", description = "FCM 알림 API")
@RestController
@RequestMapping("api/v1/notice")
public class FCMNoticeController {

    private final NoticeApplication noticeApplication;

    public FCMNoticeController(NoticeApplication noticeApplication) {
        this.noticeApplication = noticeApplication;
    }

    @Operation(summary = "알림 정보 저장", description = "발송 테스트")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/device")
    public ResponseEntity<Long> saveNoticeDeviceInfo(@RequestBody NoticeDeviceRequest request) {
        Long noticeDeviceId = noticeApplication.upsert(request);
        return ResponseEntity.ok(noticeDeviceId);
    }

    @Operation(summary = "fcm 발송 테스트", description = "발송 테스트")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping
    public String send(@RequestBody FCMNoticeRequest request) {
        return noticeApplication.sendNoticeByToken(request);
    }
}
