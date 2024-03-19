package com.dontforget.dontforget.common;

public class NotFoundAnniversaryException extends RuntimeException {

    public NotFoundAnniversaryException(Long anniversaryId) {
        super(String.format("id가 %d 인 기념일은 존재하지 않습니다.", anniversaryId));
    }
}
