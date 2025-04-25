package com.leitner.leitner_system.infrastructure.out.database.mappers;

import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.models.Quizz;
import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.CardEntity;
import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.QuizzEntity;
import com.leitner.leitner_system.infrastructure.adapter.out.database.mappers.CardEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardEntityMapperTest {
    private CardEntity cardEntity;
    private Card card;
    private CardEntityMapper cardEntityMapper = new CardEntityMapper();

    @BeforeEach
    public void setup() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        card = new Card(id, "Q1", "A1", "tech", "FIRST", now, now, now);
        cardEntity = new CardEntity(id, "Q1", "A1", "tech", "FIRST", now, now, now);
    }

    @Test
    public void shouldConvertToDomain() {
        Card domainCard = cardEntityMapper.toDomain(cardEntity);
        assertEquals(card.getId(), domainCard.getId());
        assertEquals(card.getAnswer(), domainCard.getAnswer());
        assertEquals( card.getQuestion(), domainCard.getQuestion());
        assertEquals( card.getTag(), domainCard.getTag());
        assertEquals(card.getLastAnsweredAt(), domainCard.getLastAnsweredAt());
        assertEquals( card.getCreatedAt(), domainCard.getCreatedAt());
        assertEquals( card.getUpdatedAt(), domainCard.getUpdatedAt());
    }

    @Test
    public void shouldConvertToEntity() {
        CardEntity entityCard = cardEntityMapper.toEntity(card);

        assertEquals(card.getId(), entityCard.getId());
        assertEquals(card.getAnswer(), entityCard.getAnswer());
        assertEquals(card.getQuestion(), entityCard.getQuestion());
        assertEquals(card.getTag(), entityCard.getTag());
        assertEquals(card.getLastAnsweredAt(), entityCard.getLastAnsweredAt());
        assertEquals( card.getCreatedAt(), entityCard.getCreatedAt());
        assertEquals( card.getUpdatedAt(), entityCard.getUpdatedAt());
    }
}
