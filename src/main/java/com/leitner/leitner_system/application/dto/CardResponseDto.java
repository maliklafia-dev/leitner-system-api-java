package com.leitner.leitner_system.application.dto;

import com.leitner.leitner_system.domain.models.Card;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dto used to create or update a card")
public class CardResponseDto {
    @Schema(description = "Unique identifier of the card", example = "6c10ad48-2bb8-4e2e-900a-21d62c00c07b")
    private UUID id;

    @Schema(description = "Category of the card based on the Leitner box system", example = "FIRST")
    private String category;

    @Schema(description = "The question to be asked", example = "What is encapsulation?")
    private String question;

    @Schema(description = "The expected answer for the question", example = "The process of hiding internal details from the outside world.")
    private String answer;

    @Schema(description = "The tag associated with the card", example = "OOP")
    private String tag;

    public CardResponseDto(Card card) {
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
