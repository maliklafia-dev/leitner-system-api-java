package com.leitner.leitner_system.infrastructure.out.database.entities;

import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.CardEntity;
import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.QuizzEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class QuizzEntityTest {

    @Test
    void shouldCreateAndAccessQuizzEntityFields() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        List<CardEntity> cards = new ArrayList<>();
        Map<UUID, Boolean> answers = new HashMap<>();

        QuizzEntity quizz = new QuizzEntity(id, userId, now, cards, "IN_PROGRESS", now, now, answers);

        assertEquals(id, quizz.getId());
        assertEquals(userId, quizz.getUserId());
        assertEquals("IN_PROGRESS", quizz.getStatus());
        assertEquals(now, quizz.getDate());
        assertEquals(cards, quizz.getCards());
        assertEquals(now, quizz.getStartedAt());
        assertEquals(now, quizz.getCompletedAt());
        assertEquals(answers, quizz.getAnswers());
    }
}