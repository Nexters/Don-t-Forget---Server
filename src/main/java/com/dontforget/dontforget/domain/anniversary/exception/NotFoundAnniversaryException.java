package com.dontforget.dontforget.domain.anniversary.exception;

import org.apache.coyote.BadRequestException;

public class NotFoundAnniversaryException extends BadRequestException {

  public NotFoundAnniversaryException(Long anniversaryId) {
    super(String.format("id가 %d 인 기념일은 존재하지 않습니다.", anniversaryId));
  }
}
