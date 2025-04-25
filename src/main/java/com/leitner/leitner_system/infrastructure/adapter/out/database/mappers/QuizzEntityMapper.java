package com.leitner.leitner_system.infrastructure.adapter.out.database.mappers;

import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.models.Quizz;
import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.CardEntity;
import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.QuizzEntity;

import java.util.List;
import java.util.stream.Collectors;

public class QuizzEntityMapper {

    public static Quizz toDomain(QuizzEntity entity) {
        if (entity == null) return null;
        List<Card> cards = entity.getCards()
                .stream()
                .map(CardEntityMapper::toDomain)
                .collect(Collectors.toList());

        Quizz domain = new Quizz(entity.getUserId(), cards);
        domain.setId(entity.getId());
        domain.setUserId(entity.getUserId());
        domain.setDate(entity.getDate());
        domain.setCards(cards);
        domain.setStatus(Quizz.Status.valueOf(entity.getStatus()));
        domain.setStartedAt(entity.getStartedAt());
        domain.setCompletedAt(entity.getCompletedAt());
        domain.getAnswers().putAll(entity.getAnswers());// copier les réponses


        return domain;
    }

    public static QuizzEntity toEntity(Quizz domain) {
        if (domain == null) return null;

       List<CardEntity> cards = domain.getCards()
               .stream()
               .map(CardEntityMapper::toEntity)
               .collect(Collectors.toList());

        return new QuizzEntity(
                domain.getId(),
                domain.getUserId(),
                domain.getDate(),
                cards,
                domain.getStatus().name(),
                domain.getStartedAt(),
                domain.getCompletedAt(),
                domain.getAnswers()
        );
    }
}
