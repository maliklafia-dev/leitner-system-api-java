package com.leitner.leitner_system.infrastructure.out.database.mappers;

import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.models.Quizz;
import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.CardEntity;
import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.QuizzEntity;
import com.leitner.leitner_system.infrastructure.adapter.out.database.mappers.QuizzEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuizzEntityMapperTest {
    private QuizzEntity quizzEntity;
    private Quizz quizz;
    QuizzEntityMapper quizzEntityMapper = new QuizzEntityMapper();

    @BeforeEach
    public void setup() {
        UUID userId = UUID.randomUUID();
        UUID quizzId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        quizz = new Quizz(
                quizzId,
                userId,
                now,
                List.of(
                        new Card(UUID.randomUUID(), "Q1", "A1", "tech", "FIRST", now, now, now),
                        new Card(UUID.randomUUID(), "Q2", "A2", "Law", "FIRST", now, now, now)
                ),
                Quizz.Status.PENDING,
                null,
                null,
                new HashMap<>()
        );

        quizzEntity = new QuizzEntity(
                quizzId,
                userId,
                now,
                List.of(
                        new CardEntity(UUID.randomUUID(), "Q1", "A1", "tech", "FIRST", now, now, now),
                        new CardEntity(UUID.randomUUID(), "Q2", "A2", "Law", "FIRST", now, now, now)
                ),
                "PENDING",
                null,
                null,
                new HashMap<>()
        );


    }

    @Test
    void shouldConvertToQuizz() {
        Quizz domainQuizz = quizzEntityMapper.toDomain(quizzEntity);

        assertEquals(quizz.getId(), domainQuizz.getId());
        assertEquals(quizz.getStatus(), domainQuizz.getStatus());
        assertEquals(quizz.getUserId(), domainQuizz.getUserId());
        assertEquals(quizz.getDate(), domainQuizz.getDate());
        assertEquals(quizz.getStartedAt(), domainQuizz.getStartedAt());
        assertEquals(quizz.getCompletedAt(), domainQuizz.getCompletedAt());
        assertEquals(quizz.getCards().size(), domainQuizz.getCards().size());
    }

    @Test
    void shouldConvertToQuizzEntity() {
        QuizzEntity quizzEntity = quizzEntityMapper.toEntity(quizz);
        assertEquals(quizzEntity.getId(), quizzEntity.getId());
        assertEquals(quizzEntity.getStatus(), quizzEntity.getStatus());
        assertEquals(quizzEntity.getUserId(), quizzEntity.getUserId());
        assertEquals(quizzEntity.getDate(), quizzEntity.getDate());
        assertEquals(quizzEntity.getStartedAt(), quizzEntity.getStartedAt());
        assertEquals(quizzEntity.getCompletedAt(), quizzEntity.getCompletedAt());
        assertEquals(quizzEntity.getCards().size(), quizzEntity.getCards().size());
    }
}
