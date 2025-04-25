package com.leitner.leitner_system.domain.service;

import com.leitner.leitner_system.application.dto.QuizzProgress;
import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.models.Quizz;
import com.leitner.leitner_system.domain.ports.primary.QuizzServicePort;
import com.leitner.leitner_system.domain.ports.secondary.QuizzRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class QuizzService implements QuizzServicePort {
    private QuizzRepositoryPort quizzRepository;

    public QuizzService(QuizzRepositoryPort quizzRepository) {
        this.quizzRepository = quizzRepository;
    }

    @Override
    public Quizz createQuizz(UUID userId, List<Card> cards) {
        if(cards.size() == 0 || userId == null) {
            throw new IllegalArgumentException("User ID and at least one card are required to create a quiz");
        }
        Quizz quizz = new Quizz(userId, cards);
        return this.quizzRepository.save(quizz);
    }

    @Override
    public void startQuizz(UUID quizzId) {
        Quizz quizz = this.quizzRepository.findQuizzById(quizzId);
        if(quizz != null) {
            quizz.start();
            this.quizzRepository.save(quizz);
        }
        else {
            throw new IllegalArgumentException("quizz id is mandatory");
        }
    }

    @Override
    public void completeQuizz(UUID quizzId) {
        Quizz quizz = this.quizzRepository.findQuizzById(quizzId);
        if(quizz != null) {
            quizz.complete();
            this.quizzRepository.save(quizz);
        }
        else {
            throw new IllegalArgumentException("quizz id is mandatory");
        }
    }

    @Override
    public boolean hasTakenQuizToday(UUID userId) {
        return this.quizzRepository.findAll().stream()
                .filter(q -> q.getUserId().equals(userId))
                .anyMatch(q -> {
                    LocalDateTime startedAt = q.getStartedAt();
                    return startedAt != null && startedAt.toLocalDate().equals(LocalDate.now());
                });
    }

    @Override
    public void recordAnswer(UUID quizzId, UUID cardId, boolean isCorrect) {
        Quizz quizz = this.quizzRepository.findQuizzById(quizzId);
        if(quizz != null) {
            quizz.recordAnswer(cardId, isCorrect);
            this.quizzRepository.save(quizz);
        } else {
            throw new IllegalArgumentException("quizz id is mandatory");
        }
    }

    @Override
    public QuizzProgress getQuizzProgress(UUID quizzId) {
        Quizz quizz = this.quizzRepository.findQuizzById(quizzId);
        if(quizz != null) {
            return new QuizzProgress(quizz);
        }else{
            throw new IllegalArgumentException("quizz id is mandatory");
        }
    }
}
