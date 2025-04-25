package com.leitner.leitner_system.application.dto;

import com.leitner.leitner_system.domain.models.Card;

import java.util.UUID;

public class CardUserData {
    private UUID id;
    private String category;
    private String question;
    private String answer;
    private String tag;

    public CardUserData(String question, String answer, String tag) {
        this.question = question;
        this.answer = answer;
        this.tag = tag;
    }

    public CardUserData() {
    }

    public CardUserData(UUID id, String category, String question, String answer, String tag) {
        this.id = id;
        this.category = category;
        this.question = question;
        this.answer = answer;
        this.tag = tag;
    }

    public CardUserData(Card card) {
        this.id = card.getId();
        this.category = card.getCategory();
        this.question = card.getQuestion();
        this.answer = card.getAnswer();
        this.tag = card.getTag();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
