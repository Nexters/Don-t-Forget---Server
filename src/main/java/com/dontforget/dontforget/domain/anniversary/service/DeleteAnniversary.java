package com.dontforget.dontforget.domain.anniversary.service;

import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class DeleteAnniversary {
    private final AnniversaryRepository anniversaryRepository;

    public void deleteAnniversary(final Long anniversaryId) {
        Anniversary anniversary = anniversaryRepository.findById(anniversaryId);

        anniversaryRepository.delete(anniversary);
    }
}
