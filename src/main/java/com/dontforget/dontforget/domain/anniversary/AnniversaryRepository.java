package com.dontforget.dontforget.domain.anniversary;

public interface AnniversaryRepository {
    Anniversary findById(final Long id);

    Long save(final Anniversary anniversary);
}
