package com.leitner.leitner_system.infrastructure.adapter.out.database.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "Quizz")
public class QuizzEntity {
    @Id
    private UUID id;
    private String status;
    private UUID userId;
    private LocalDateTime date;

    @OneToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name="quizz_cards",
            joinColumns = @JoinColumn(name = "quizz_id"),
            inverseJoinColumns = @JoinColumn(name = "cards_id")
    )

    private List<CardEntity> cards;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    @ElementCollection
    @CollectionTable(
            name="quizz_answers",
            joinColumns = @JoinColumn(name="quizz_id")
    )
    @MapKeyColumn(name="card_id")
    @Column(name = "is_correct")
    private Map<UUID, Boolean> answers = new HashMap();

    public QuizzEntity() {}

    public QuizzEntity(UUID id, List<CardEntity> cards) {
        this.id = id;
        this.cards = cards;
    }

    public QuizzEntity(UUID id, UUID userId,
                       LocalDateTime date, List<CardEntity> cards,
                       String status, LocalDateTime startedAt,
                       LocalDateTime completedAt, Map<UUID, Boolean> answers) {
        this.id = id;
        this.status = status;
        this.userId = userId;
        this.date = date;
        this.cards = cards;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.answers = answers;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<CardEntity> getCards() {
        return cards;
    }

    public void setCards(List<CardEntity> cards) {
        this.cards = cards;
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

    public void setAnswers(Map<UUID, Boolean> answers) {
        this.answers = answers;
    }
}
