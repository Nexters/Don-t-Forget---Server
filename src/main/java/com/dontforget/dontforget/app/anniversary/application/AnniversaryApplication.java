package com.dontforget.dontforget.app.anniversary.application;

import com.dontforget.dontforget.domain.anniversary.query.CreateAnniversaryQuery;
import com.dontforget.dontforget.domain.anniversary.service.CreateAnniversary;
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
