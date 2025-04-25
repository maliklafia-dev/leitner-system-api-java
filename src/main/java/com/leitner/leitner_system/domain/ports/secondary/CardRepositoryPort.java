package com.leitner.leitner_system.domain.ports.secondary;

import com.leitner.leitner_system.domain.models.Card;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CardRepositoryPort {
    Card save(Card card);
    Card findCardById(UUID id);
    List<Card> findAllCards();
    List<Card> findCardsByTag(List<String> tag);
    List<Card> findCardsForQuizzByDate(LocalDate date);
}
