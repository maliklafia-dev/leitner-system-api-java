package com.leitner.leitner_system.domain.ports.primary;

import com.leitner.leitner_system.application.dto.QuizzProgress;
import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.models.Quizz;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface QuizzServicePort {
    Quizz createQuizz(UUID userId, List<Card> cards);
    void startQuizz(UUID quizzId);
    void completeQuizz(UUID quizzId);
    boolean hasTakenQuizToday(UUID userId);
    void recordAnswer(UUID quizzId, UUID cardId, boolean isCorrect);
    QuizzProgress getQuizzProgress(UUID quizzId);
}
