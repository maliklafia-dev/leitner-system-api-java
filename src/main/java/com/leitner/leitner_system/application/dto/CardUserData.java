package com.leitner.leitner_system.application.dto;

import com.leitner.leitner_system.domain.models.Card;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class CardUserData {
    private UUID id;
    private String category;

    @Schema(description = "Question to be asked to the user during a quiz", example = "What is polymorphism?")
    private String question;

    @Schema(description = "Expected answer for the question", example = "An object-oriented concept where objects can take multiple forms.")
    private String answer;

    @Schema(description = "A tag to group cards on the same topic", example = "Java")
    private String tag;

    public CardUserData(String question, String answer, String tag) {
        this.question = question;
        this.answer = answer;
        this.tag = tag;
    }

    public CardUserData() {
    }

    public CardUserData(UUID id, String question, String answer, String tag) {
        this.id = id;
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
