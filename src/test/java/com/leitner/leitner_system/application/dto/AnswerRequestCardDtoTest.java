package com.leitner.leitner_system.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnswerRequestCardDtoTest {

    @Test
    void shouldSetAndGetIsValid() {
        AnswerRequestCardDto dto = new AnswerRequestCardDto();
        dto.setIsValid(true);
        assertTrue(dto.isValid());

        dto.setIsValid(false);
        assertFalse(dto.isValid());
    }
}