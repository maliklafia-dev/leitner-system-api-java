package com.leitner.leitner_system.application.dto;

import com.leitner.leitner_system.domain.models.Quizz;

import java.time.LocalDateTime;
import java.util.UUID;

public class QuizzProgress {
    private UUID quizzId;
    private final int totalCards;
    private final int answerdCards;
    private final int remainingCards;
    private Quizz.Status status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    public QuizzProgress(Quizz quizz) {
        this.quizzId = quizz.getId();
        this.totalCards = quizz.getCards().size();
        this.answerdCards = quizz.getAnsweredCards().size();
        this.remainingCards = quizz.getRemainingCards().size();
        this.status = quizz.getStatus();
        this.startedAt = quizz.getStartedAt();
        this.completedAt = quizz.getCompletedAt();
    }

    public UUID getQuizzId() {
        return quizzId;
    }

    public void setQuizzId(UUID quizzId) {
        this.quizzId = quizzId;
    }

    public int getTotalCards() {
        return totalCards;
    }

    public int getAnswerdCards() {
        return answerdCards;
    }

    public int getRemainingCards() {
        return remainingCards;
    }

    public Quizz.Status getStatus() {
        return status;
    }

    public void setStatus(Quizz.Status status) {
        this.status = status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
