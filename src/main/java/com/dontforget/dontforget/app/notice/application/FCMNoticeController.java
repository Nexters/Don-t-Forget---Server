package com.dontforget.dontforget.app.notice.application;

import com.dontforget.dontforget.app.notice.api.request.FCMNoticeRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notice")
public class FCMNoticeController {

    private FCMNoticeService sender;

    public FCMNoticeController(FCMNoticeService sender) {
        this.sender = sender;
    }

    @PostMapping
    public String send(@RequestBody FCMNoticeRequest request) {
        return sender.sendNoticeByToken(request);
    }
}
