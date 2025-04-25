package com.leitner.leitner_system.application.dto;

import com.leitner.leitner_system.domain.models.Card;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CardUserDataTest {

    @Test
    void shouldCreateCardUserDataFromCard() {
        UUID id = UUID.randomUUID();
        Card card = new Card("What is Java?", "A programming language", "programming");
        card.setId(id);
        card.setCategory("FIRST");

        CardUserData dto = new CardUserData(card);

        assertEquals(id, dto.getId());
        assertEquals("FIRST", dto.getCategory());
        assertEquals("What is Java?", dto.getQuestion());
        assertEquals("A programming language", dto.getAnswer());
        assertEquals("programming", dto.getTag());
    }

    @Test
    void shouldSetAndGetFields() {
        UUID id = UUID.randomUUID();
        CardUserData dto = new CardUserData();

        dto.setId(id);
        dto.setCategory("SECOND");
        dto.setQuestion("Q?");
        dto.setAnswer("A");
        dto.setTag("tag1");

        assertEquals(id, dto.getId());
        assertEquals("SECOND", dto.getCategory());
        assertEquals("Q?", dto.getQuestion());
        assertEquals("A", dto.getAnswer());
        assertEquals("tag1", dto.getTag());
    }
}