package com.leitner.leitner_system.domain.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/***
 * Classe card contient les champs, méthodes et règles de validaion concernant une carte
 */
public class Card {
    private static final List<String> CATEGORIES = List.of(
            "FIRST", "SECOND", "THIRD", "FOURTH", "FIFTH", "SEVENTH", "DONE"
    );

    private UUID id;
    private String question;
    private String answer;
    private String tag;
    private String category;
    private LocalDateTime updatedAt;
    private LocalDateTime lastAnsweredAt;
    private LocalDateTime createdAt;

    public Card() {
    }

    public Card(String question, String answer, String tag) {
        if(question == null || question.isEmpty()) {
            throw new IllegalArgumentException("Question cannot be null or empty");
        }
        if(answer == null || answer.isEmpty()) {
            throw new IllegalArgumentException("Answer cannot be null or empty");
        }

        this.id = UUID.randomUUID();
        this.category = "FIRST";
        this.question = question;
        this.answer = answer;
        this.tag = tag;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void answeredCorrectly() {
        int currentIndex = CATEGORIES.indexOf(this.category);
        if(currentIndex < CATEGORIES.size() - 1) {
            this.category = CATEGORIES.get(currentIndex + 1);
        }
        this.lastAnsweredAt = LocalDateTime.now();
    }

    public void answeredIncorrectly() {
        this.category = "FIRST";
        this.lastAnsweredAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastAnsweredAt() {
        return lastAnsweredAt;
    }

    public void setLastAnsweredAt(LocalDateTime lastAnsweredAt) {
        this.lastAnsweredAt = lastAnsweredAt;
    }

    public Card(UUID id,
                String question,
                String answer, String tag,
                String category, LocalDateTime updatedAt,
                LocalDateTime lastAnsweredAt, LocalDateTime createdAt) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.tag = tag;
        this.category = category;
        this.updatedAt = updatedAt;
        this.lastAnsweredAt = lastAnsweredAt;
        this.createdAt = createdAt;
    }
}
