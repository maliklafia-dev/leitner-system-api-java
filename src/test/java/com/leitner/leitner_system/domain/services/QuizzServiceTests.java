package com.leitner.leitner_system.domain.services;

import com.leitner.leitner_system.application.dto.QuizzProgress;
import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.models.Quizz;
import com.leitner.leitner_system.domain.ports.secondary.QuizzRepositoryPort;
import com.leitner.leitner_system.domain.service.QuizzService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class QuizzServiceTests {
    private QuizzService quizzService;
    private QuizzRepositoryPort quizzRepository;
    private Quizz quizz;

    @BeforeEach
    void setUp() {
        this.quizzRepository = Mockito.mock(QuizzRepositoryPort.class);
        this.quizzService = new QuizzService(this.quizzRepository);
        UUID userId = UUID.randomUUID();
        Card card1 = new Card("Q1", "A1", "tag1");
        Card card2 = new Card("Q2", "A2", "tag2");
        List<Card> cards = Arrays.asList(card1, card2);
        this.quizz = new Quizz(userId, cards);
    }

    @Test
    void shouldCreateQuizzWithDefaults() {
        UUID userId = UUID.randomUUID();
        System.out.println(userId);
        Card card1 = new Card("Q1", "A1", "tag1");
        Card card2 = new Card("Q2", "A2", "tag2");
        List<Card> cards = Arrays.asList(card1, card2);
        Quizz expectedQuizz = new Quizz(userId, cards);

        when(this.quizzRepository.save(any(Quizz.class))).thenReturn(expectedQuizz);

        Quizz quizz = this.quizzService.createQuizz(userId, cards);

        assertNotNull(quizz);
        assertNotNull(quizz.getId());
        assertThat(quizz.getRemainingCards()).isEqualTo(cards);
        verify(this.quizzRepository, times(1)).save(any(Quizz.class));
    }

    @Test
    void shouldThrowWhenCreatingQuizzWithNoCards() {
        UUID userId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () ->
                quizzService.createQuizz(userId, Collections.emptyList())
        );
    }

    @Test
    void shouldStartQuizzAndSaveIt() {
       when(this.quizzRepository.findQuizzById(this.quizz.getId())).thenReturn(quizz);
       when(this.quizzRepository.save(this.quizz)).thenReturn(quizz);

       quizzService.startQuizz(this.quizz.getId());

       assertEquals(LocalDate.now(), this.quizz.getStartedAt().toLocalDate());
       assertEquals(Quizz.Status.IN_PROGRESS, this.quizz.getStatus());
       verify(this.quizzRepository, times(1)).save(this.quizz);
    }

    @Test
    void shouldThrowWhenStartingNonExistingQuizz() {
        UUID quizId = UUID.randomUUID();
        when(quizzRepository.findQuizzById(quizId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                quizzService.startQuizz(quizId)
        );
    }

    @Test
    void shouldRecordAnswerAndSaveQuizz() {
        UUID quizId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();
        Quizz quizz = mock(Quizz.class);

        when(quizzRepository.findQuizzById(quizId)).thenReturn(quizz);

        quizzService.recordAnswer(quizId, cardId, true);

        verify(quizz).recordAnswer(cardId, true);
        verify(quizzRepository).save(quizz);
    }

    @Test
    void shouldGetQuizzProgressForValidId() {
        UUID quizId = UUID.randomUUID();
        Quizz quizz = new Quizz(UUID.randomUUID(), List.of(new Card("Q", "A", "tag")));

        when(quizzRepository.findQuizzById(quizId)).thenReturn(quizz);

        QuizzProgress progress = quizzService.getQuizzProgress(quizId);

        assertNotNull(progress);
        assertEquals(1, progress.getTotalCards());
    }

    @Test
    void shouldReturnTrueIfUserHasTakenQuizzToday() {
        Quizz quizz = mock(Quizz.class);
        UUID userId = UUID.randomUUID();

        when(quizzRepository.findAll()).thenReturn(List.of(quizz));
        when(quizz.getUserId()).thenReturn(userId);
        when(quizz.getStartedAt()).thenReturn(LocalDateTime.now());

        boolean result = quizzService.hasTakenQuizToday(userId);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseIfUserHasNotTakenQuizzToday() {
        UUID userId = UUID.randomUUID();
        Quizz quizz = mock(Quizz.class);

        when(quizzRepository.findAll()).thenReturn(List.of(quizz));
        when(quizz.getUserId()).thenReturn(userId);
        when(quizz.getStartedAt()).thenReturn(LocalDateTime.now());

        when(quizzRepository.findAll()).thenReturn(Collections.emptyList());

        boolean result = quizzService.hasTakenQuizToday(userId);
        assertFalse(result);
    }

    @Test
    void shouldCompleteQuizzAndSaveIt() {
        UUID quizId = UUID.randomUUID();
        Quizz quizz = mock(Quizz.class);

        when(quizzRepository.findQuizzById(quizId)).thenReturn(quizz);

        quizzService.completeQuizz(quizId);

        verify(quizz).complete();
        verify(quizzRepository).save(quizz);
    }

    @Test
    void shouldThrowWhenCompletingNonExistingQuizz() {
        UUID quizId = UUID.randomUUID();
        when(quizzRepository.findQuizzById(quizId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                quizzService.completeQuizz(quizId)
        );
    }

    @Test
    void shouldThrowWhenRecordingAnswerWithInvalidQuizzId() {
        UUID quizzId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();

        when(quizzRepository.findQuizzById(quizzId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                quizzService.recordAnswer(quizzId, cardId, true)
        );
    }

    @Test
    void shouldThrowWhenGettingProgressForInvalidQuizzId() {
        UUID quizId = UUID.randomUUID();

        when(quizzRepository.findQuizzById(quizId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                quizzService.getQuizzProgress(quizId)
        );
    }

}
