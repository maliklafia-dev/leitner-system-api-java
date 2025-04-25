package com.leitner.leitner_system.domain.models;

import java.util.UUID;


public class QuizAnswer {
    private UUID cardId;
    private boolean isCorrect;

    public QuizAnswer(UUID cardId, boolean isCorrect) {
        this.cardId = cardId;
        this.isCorrect = isCorrect;
    }

    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
