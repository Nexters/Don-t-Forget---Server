package com.dontforget.dontforget.app.anniversary.application;

import com.dontforget.dontforget.app.anniversary.application.query.CreateQuery;
import com.dontforget.dontforget.app.anniversary.application.service.CreateAnniversary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnniversaryApplication {

  private final CreateAnniversary createAnniversary;

  public Long create(CreateQuery query) {
    return createAnniversary.create(query);
  }
}
