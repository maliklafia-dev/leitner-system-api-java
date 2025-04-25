package com.leitner.leitner_system.application.dto;

import com.leitner.leitner_system.domain.models.Card;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CardResponseDtoTest {

    @Test
    void shouldMapFromCardCorrectly() {
        UUID id = UUID.randomUUID();
        Card card = new Card("What is Java?", "A language", "programming");
        card.setId(id);
        card.setCategory("FIRST");

        CardResponseDto dto = new CardResponseDto(card);

        assertEquals(id, dto.getId());
        assertEquals("FIRST", dto.getCategory());
        assertEquals("What is Java?", dto.getQuestion());
        assertEquals("A language", dto.getAnswer());
        assertEquals("programming", dto.getTag());
    }
}