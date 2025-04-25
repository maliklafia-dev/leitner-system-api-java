package com.leitner.leitner_system.domain.models;

import java.time.LocalDateTime;
import java.util.*;

/***
 * Classe quizz contient toutes les champs et règles de validation concernent un quizz
 */
public class Quizz {
    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    };

    private UUID id;
    private UUID userId;
    private LocalDateTime date;
    private List<Card> cards;
    private Status status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private final Map<UUID, Boolean> answers;

    public Quizz(UUID userId, List<Card> cards) {
        if (userId == null) throw new IllegalArgumentException("userId is required");
        if (cards == null) throw new IllegalArgumentException("cards is required");

        this.id = UUID.randomUUID();
        this.status = Status.PENDING;
        this.userId = userId;
        this.cards = new ArrayList<>(cards);
        this.date = LocalDateTime.now();
        this.answers = new HashMap<>();
    }

    public Quizz(UUID id, UUID userId,
                 LocalDateTime date, List<Card> cards,
                 Status status, LocalDateTime startedAt,
                 LocalDateTime completedAt, Map<UUID, Boolean> answers) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.cards = cards;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.answers = answers;
    }

    public void start() {
        if(this.status != Status.PENDING) {
            throw new IllegalStateException("Quizz must pending to start");
        }
        this.status = Status.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();
    }

    public void recordAnswer(UUID cardId, boolean isCorrect) {
        if(this.status == Status.PENDING) {
            throw new IllegalStateException("Quizz must pending to start");
        }
        if(cards.stream().noneMatch((card) -> cardId.equals(card.getId()))) {
            throw new IllegalStateException("Card not found in quizz");
        }

        answers.put(cardId, isCorrect);
    }

    public void complete() {
        if(this.status != Status.IN_PROGRESS) {
            throw new IllegalStateException("Quizz must be started to complete");
        }
        if(getRemainingCards().size() > 0) {
            throw new IllegalStateException("All cards must be answered");
        }
        this.status = Status.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    public List<Card> getRemainingCards() {
        return cards.stream()
                .filter(card -> !answers.containsKey(card.getId()))
                .toList();
    }

    public List<Card> getAnsweredCards() {
        return cards.stream()
                .filter(card -> answers.containsKey(card.getId()))
                .toList();
    }

    public List<QuizAnswer> getRecordedAnswers() {
        return answers.entrySet().stream()
                .map(entry -> new QuizAnswer(entry.getKey(), entry.getValue()))
                .toList();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    public Map<UUID, Boolean> getAnswers() {
        return answers;
    }

}
