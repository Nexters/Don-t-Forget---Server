package com.dontforget.dontforget.domain.notice;

import lombok.Getter;

@Getter
public enum NoticeType {
    ONE_MONTH(30),
    TWO_WEEKS(14),
    ONE_WEEKS(7),
    THREE_DAYS(3),
    ONE_DAYS(1),
    D_DAY(0),
    ;

    private final int day;

    NoticeType(int day) {
        this.day = day;
    }
}
