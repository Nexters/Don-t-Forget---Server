package com.dontforget.dontforget.app.notice.application;

import com.dontforget.dontforget.app.notice.api.request.FCMNoticeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "notice", description = "FCM 알림 API")
@RestController
@RequestMapping("api/v1/notice")
public class FCMNoticeController {

    private FCMNoticeService sender;

    public FCMNoticeController(FCMNoticeService sender) {
        this.sender = sender;
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
