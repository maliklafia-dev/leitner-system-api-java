package com.leitner.leitner_system.infrastructure.adapter.out.database.mappers;

import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.CardEntity;

public class CardEntityMapper {

    public static Card toDomain(CardEntity cardEntity) {
        if(cardEntity == null) {
            return null;
        }
        return new Card(
                cardEntity.getId(),
                cardEntity.getQuestion(),
                cardEntity.getAnswer(),
                cardEntity.getTag(),
                cardEntity.getCategory(),
                cardEntity.getLastAnsweredAt(),
                cardEntity.getCreatedAt(),
                cardEntity.getLastAnsweredAt()
        );
    }

    public static CardEntity toEntity(Card domain) {
        if(domain == null) {
            return null;
        }
        return new CardEntity(
                domain.getId(),
                domain.getQuestion(),
                domain.getAnswer(),
                domain.getTag(),
                domain.getCategory(),
                domain.getUpdatedAt(),
                domain.getLastAnsweredAt(),
                domain.getCreatedAt()
        );
    }
}
