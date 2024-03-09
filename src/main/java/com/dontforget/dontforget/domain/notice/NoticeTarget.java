package com.dontforget.dontforget.domain.notice;

import com.dontforget.dontforget.domain.anniversary.Anniversary;
import lombok.Getter;

@Getter
public class NoticeTarget {

    private Long noticeId;
    private Anniversary anniversary;
    private String title;
    private String content;
    private String deviceUuid;
    private NoticeType noticeType;

    public NoticeTarget(Long noticeId, Anniversary anniversary, String title, String content,
        String deviceUuid, NoticeType noticeType) {
        this.noticeId = noticeId;
        this.anniversary = anniversary;
        this.title = title;
        this.content = content;
        this.deviceUuid = deviceUuid;
        this.noticeType = noticeType;
    }

    public static NoticeTarget of(Anniversary anniversary, Notice it) {
        return new NoticeTarget(it.getId(),
            anniversary,
            anniversary.getTitle(),
            anniversary.getContent(),
            anniversary.getDeviceUuid(),
            it.getNoticeType());
    }

    public boolean isDDay() {
        return noticeType == NoticeType.D_DAY;
    }


    public String getMessage() {
        if (noticeType == NoticeType.D_DAY) {
            return "오늘은 " + title + " 🎉\n" + "사랑하는 사람과, 행복한 하루 보내세요 💓";
        }

        if (content.isBlank() || content.isEmpty()) {
            return title + "까지 " + noticeType.getDay() + "일 남았습니다.\n" + " 즐거운 하루를 준비해보세요😄";
        }

        return title + "까지 " + noticeType.getDay() + "일 남았습니다.\n 즐거운 기념일을 위해서 \n " + content
            + "\n 잊지말고 준비해볼까요?😄";
    }

}
