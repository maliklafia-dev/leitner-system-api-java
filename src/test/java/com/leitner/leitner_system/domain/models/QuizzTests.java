package com.leitner.leitner_system.domain.models;

import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.models.Quizz;
import com.leitner.leitner_system.domain.models.QuizAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

public class QuizzTests {

    private UUID userId;
    private Card card1;
    private Card card2;
    private List<Card> cards;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        card1 = new Card("Q1", "A1", "tag1");
        card2 = new Card("Q2", "A2", "tag2");
        cards = List.of(card1, card2);
    }

    @Test
    void shouldCreateQuizzWithValidData() {
        Quizz quiz = new Quizz(userId, cards);

        assertEquals(Quizz.Status.PENDING, quiz.getStatus());
        assertEquals(2, quiz.getCards().size());
        assertEquals(2, quiz.getRemainingCards().size());
        assertNotNull(quiz.getId());
    }

    @Test
    void shouldStartQuizWhenPending() {
        Quizz quiz = new Quizz(userId, cards);
        quiz.start();

        assertEquals(Quizz.Status.IN_PROGRESS, quiz.getStatus());
        assertNotNull(quiz.getStartedAt());
    }

    @Test
    void shouldThrowWhenStartingAlreadyStartedQuiz() {
        Quizz quiz = new Quizz(userId, cards);
        quiz.start();

        IllegalStateException ex = assertThrows(IllegalStateException.class, quiz::start);
        assertEquals("Quizz must pending to start", ex.getMessage());
    }

    @Test
    void shouldRecordAnswerIfQuizStarted() {
        Quizz quiz = new Quizz(userId, cards);
        quiz.start();
        quiz.recordAnswer(card1.getId(), true);

        assertEquals(1, quiz.getAnsweredCards().size());
        assertEquals(1, quiz.getRemainingCards().size());
    }

    @Test
    void shouldThrowIfRecordingAnswerBeforeStart() {
        Quizz quiz = new Quizz(userId, cards);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                quiz.recordAnswer(card1.getId(), true)
        );
        assertEquals("Quizz must pending to start", ex.getMessage());
    }

    @Test
    void shouldThrowIfAnsweringCardNotInQuiz() {
        Quizz quiz = new Quizz(userId, cards);
        quiz.start();

        UUID fakeCardId = UUID.randomUUID();
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                quiz.recordAnswer(fakeCardId, true)
        );
        assertEquals("Card not found in quizz", ex.getMessage());
    }

    @Test
    void shouldCompleteQuizOnlyWhenAllCardsAnswered() {
        Quizz quiz = new Quizz(userId, cards);
        quiz.start();
        quiz.recordAnswer(card1.getId(), true);
        quiz.recordAnswer(card2.getId(), false);

        quiz.complete();

        assertEquals(Quizz.Status.COMPLETED, quiz.getStatus());
        assertNotNull(quiz.getCompletedAt());
    }

    @Test
    void shouldThrowIfCompletingWithoutAnsweringAllCards() {
        Quizz quiz = new Quizz(userId, cards);
        quiz.start();
        quiz.recordAnswer(card1.getId(), true);

        IllegalStateException ex = assertThrows(IllegalStateException.class, quiz::complete);
        assertEquals("All cards must be answered", ex.getMessage());
    }

    @Test
    void shouldReturnCorrectQuizzAnswers() {
        Quizz quizz = new Quizz(userId, cards);
        quizz.start();
        quizz.recordAnswer(card1.getId(), true);
        quizz.recordAnswer(card2.getId(), false);

        List<QuizAnswer> quizzAnswers = quizz.getRecordedAnswers();
        assertEquals(2, quizzAnswers.size());
        assertTrue(quizzAnswers.stream().anyMatch(answer -> answer.getCardId().equals(card1.getId()) && answer.isCorrect() ));
        assertTrue(quizzAnswers.stream().anyMatch(answer -> answer.getCardId().equals(card2.getId()) && !answer.isCorrect()));
    }

    @Test
    void shouldCreateQuizAnswerAndSetValues() {
        UUID cardId = UUID.randomUUID();
        boolean isCorrect = true;

        QuizAnswer answer = new QuizAnswer(cardId, isCorrect);

        assertEquals(cardId, answer.getCardId());
        assertTrue(answer.isCorrect());
    }

    @Test
    void shouldNotRecordAnswerIfCardNotInRemaining() {
        Quizz quizz = new Quizz(UUID.randomUUID(), List.of(new Card("Q", "A", "tag")));
        quizz.start();

        UUID unknownCardId = UUID.randomUUID();
        //quizz.recordAnswer(unknownCardId, true);
        assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(() -> {
            quizz.recordAnswer(unknownCardId, true);
            // Vérifie que la carte n'a pas été supprimée
            assertEquals(1, quizz.getRemainingCards().size());
        });
    }

}
