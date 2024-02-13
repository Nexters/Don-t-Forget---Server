package com.dontforget.dontforget.app.notice.application;

import com.dontforget.dontforget.app.notice.api.request.FCMNoticeRequest;
import com.dontforget.dontforget.app.notice.api.request.NoticeDeviceRequest;
import com.dontforget.dontforget.domain.notice.service.CreateNoticeDevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "notice", description = "FCM 알림 API")
@RestController
@RequestMapping("api/v1/notice")
public class FCMNoticeController {

    private final FCMNoticeService sender;
    private final CreateNoticeDevice createNoticeDevice;

    public FCMNoticeController(FCMNoticeService sender, CreateNoticeDevice createNoticeDevice) {
        this.sender = sender;
        this.createNoticeDevice = createNoticeDevice;
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
        Long noticeDeviceId = createNoticeDevice.create(request);
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
        return sender.sendNoticeByToken(request);
    }
}
