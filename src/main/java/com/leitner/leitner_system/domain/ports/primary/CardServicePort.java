package com.leitner.leitner_system.domain.ports.primary;

import com.leitner.leitner_system.domain.models.Card;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CardServicePort {
    List<Card> getCards();
    Card getCardById(UUID id);
    Card createCard(Card cardData);
    Card updateCard(UUID cardId, Card card);
    Card answerCard(UUID id, boolean isCorrect);
    List<Card> getCardsByTag(List<String> tag);
    List<Card> getCardsForQuizz(LocalDate date);

}
