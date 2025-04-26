package com.leitner.leitner_system.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CardTests {
    private Card card;
    @BeforeEach
    void setUp() {
     card = new Card("What is Java ?", "Java is a programming langage", "Tech");
    }

    @Test
    void shouldCreateCardWithDefaults() {

        assertNotNull(card);
        assertNotNull(card.getId());
        assertNotNull(card.getCreatedAt());
        assertNull(card.getLastAnsweredAt());
        assertNotNull(card.getUpdatedAt());
        System.out.println(card.getUpdatedAt());
        assertEquals("What is Java ?", card.getQuestion());
        assertEquals("Java is a programming langage", card.getAnswer());
        assertEquals("Tech", card.getTag());
        assertEquals("FIRST", card.getCategory());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenQuestionIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Card card = new Card("", "Java is a programming langage", "Tech");
        });
        assertEquals("Question cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAnswerIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Card card = new Card("What is Java ?", "", "Tech");
        });
        assertEquals("Answer cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldMoveToNextCategoryAndChangeLastAnsweredWhenAnswerIsCorrect() {
        card.answeredCorrectly();
        assertEquals("SECOND", card.getCategory());
        assertEquals(LocalDate.now(), card.getLastAnsweredAt().toLocalDate());
    }

    @Test
    void shouldNotGoPastDoneCategory() {
        card.setCategory("DONE");
        card.answeredCorrectly();
        assertEquals("DONE", card.getCategory());
    }

    @Test
    void shouldResetCategoryToFirstWhenIncorrectlyAnswered() {
        card.setCategory("SECOND");
        card.answeredIncorrectly();
        assertEquals("FIRST", card.getCategory());
    }

    @Test
    void shouldNotPromoteWhenAlreadyDone() {
        Card card = new Card("Q", "A", "tag");
        card.setCategory("DONE");
        card.answeredCorrectly();
        assertEquals("DONE", card.getCategory());
    }

}
