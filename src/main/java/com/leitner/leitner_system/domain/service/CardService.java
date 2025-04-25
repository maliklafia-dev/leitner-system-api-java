package com.leitner.leitner_system.domain.service;

import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.ports.primary.CardServicePort;
import com.leitner.leitner_system.domain.ports.secondary.CardRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardService implements CardServicePort {
    private CardRepositoryPort cardRepository;

    public CardService(CardRepositoryPort cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<Card> getCards() {
        return this.cardRepository.findAllCards();
    }

    public Card getCardById(UUID id) {
        return id == null ? null : this.cardRepository.findCardById(id);
    }

    @Override
    public Card createCard(Card cardData) {
        if(cardData == null) {
            return null;
        }
        Card card = new Card(cardData.getQuestion(), cardData.getAnswer(), cardData.getTag());
        this.cardRepository.save(card);
        return card;
    }

    @Override
    public Card updateCard(UUID cardId, Card card) {
        if(cardId == null) {
            return null;
        }

        Card updatedCard = getCardById(cardId);

        if(updatedCard != null) {
            updatedCard.setQuestion(card.getQuestion());
            updatedCard.setAnswer(card.getAnswer());
            updatedCard.setTag(card.getTag());
            updatedCard.setCategory(card.getCategory());
            updatedCard.setUpdatedAt(LocalDateTime.now());

            this.cardRepository.save(updatedCard);
            return updatedCard;
        }
        return null;
    }

    @Override
    public Card answerCard(UUID id, boolean isCorrect) {
        Card card = getCardById(id);

        if(card != null) {
            if(!isCorrect) {
                card.answeredIncorrectly();
            } else {
                card.answeredCorrectly();
                this.cardRepository.save(card);
                return card;
            }
        }
        return null;
    }

    @Override
    public List<Card> getCardsByTag(List<String> tags) {
        if(tags == null) {
            return null;
        }
        return this.cardRepository.findCardsByTag(tags);
    }

    @Override
    public List<Card> getCardsForQuizz(LocalDate date) {
        return cardRepository.findCardsForQuizzByDate(date);
    }
}
