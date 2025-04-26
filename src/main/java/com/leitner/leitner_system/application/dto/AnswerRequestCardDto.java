package com.leitner.leitner_system.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO used when answering a card")
public class AnswerRequestCardDto {

    @Schema(description = "Describe if the answer is valid or not", example = "True")
    private boolean isValid;

    public boolean isValid() {
        return isValid;
    }
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
}
