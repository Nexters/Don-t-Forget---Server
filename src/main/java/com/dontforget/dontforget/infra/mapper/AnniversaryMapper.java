package com.dontforget.dontforget.infra.mapper;

import com.dontforget.dontforget.common.CardType;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.infra.jpa.anniversary.AnniversaryEntity;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AnniversaryMapper {
    public AnniversaryEntity toEntity(final Anniversary anniversary) {
        return new AnniversaryEntity(
            anniversary.getId(),
            anniversary.getTitle(),
            anniversary.getContent(),
            anniversary.getDeviceUuid(),
            anniversary.getLunarDate(),
            anniversary.getSolarDate(),
            anniversary.getCardType().name()
        );
    }

    public Anniversary toDomain(final AnniversaryEntity anniversaryEntity, final List<Notice> notices) {
        return new Anniversary(
            anniversaryEntity.getId(),
            anniversaryEntity.getTitle(),
            anniversaryEntity.getContent(),
            anniversaryEntity.getDeviceUuid(),
            anniversaryEntity.getLunarDate(),
            anniversaryEntity.getSolarDate(),
            notices,
            CardType.valueOf(anniversaryEntity.getCardType())
        );
    }

    public Anniversary toDomain(final AnniversaryEntity anniversaryEntity) {
        return new Anniversary(
            anniversaryEntity.getId(),
            anniversaryEntity.getTitle(),
            anniversaryEntity.getContent(),
            anniversaryEntity.getDeviceUuid(),
            anniversaryEntity.getLunarDate(),
            anniversaryEntity.getSolarDate(),
            null,
            CardType.valueOf(anniversaryEntity.getCardType())
        );
    }
}
