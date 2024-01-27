package com.dontforget.dontforget.app.anniversary.application;

import com.dontforget.dontforget.app.anniversary.application.query.CreateAnniversaryQuery;
import com.dontforget.dontforget.app.anniversary.application.service.CreateAnniversary;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnniversaryApplication {

  private final CreateAnniversary createAnniversary;

  @Transactional
  public Long create(CreateAnniversaryQuery query) {
    return createAnniversary.create(query);
  }
}
