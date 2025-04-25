package com.leitner.leitner_system.infrastructure.out.database.entities;

import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.CardEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CardEntityTest {

    @Test
    void shouldCreateAndAccessCardEntityFields() {
        UUID id = UUID.randomUUID();
        String question = "What is Java?";
        String answer = "A programming language.";
        String tag = "language";
        String category = "FIRST";
        LocalDateTime now = LocalDateTime.now();

        CardEntity card = new CardEntity(id, question, answer, tag, category, now, now, now);

        assertEquals(id, card.getId());
        assertEquals(question, card.getQuestion());
        assertEquals(answer, card.getAnswer());
        assertEquals(tag, card.getTag());
        assertEquals(category, card.getCategory());
        assertEquals(now, card.getCreatedAt());
        assertEquals(now, card.getLastAnsweredAt());
        assertEquals(now, card.getUpdatedAt());
    }
}
