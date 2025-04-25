package com.leitner.leitner_system.infrastructure.adapter.out.database.adapter;

import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.ports.secondary.CardRepositoryPort;
import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.CardEntity;
import com.leitner.leitner_system.infrastructure.adapter.out.database.mappers.CardEntityMapper;
import com.leitner.leitner_system.infrastructure.adapter.out.database.repository.SpringDataCardRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaCardRepositoryAdapter implements CardRepositoryPort {
    private  SpringDataCardRepository repository;

    public JpaCardRepositoryAdapter(SpringDataCardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Card save(Card card) {
        CardEntity cardEntity = CardEntityMapper.toEntity(card);
        return CardEntityMapper.toDomain(repository.save(cardEntity));
    }

    @Override
    public Card findCardById(UUID id) {
        return repository.findById(id)
                .map(CardEntityMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Card> findAllCards() {
        return repository.findAll()
                .stream()
                .map(CardEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Card> findCardsByTag(List<String> tags) {
        return repository.findByTagIn(tags)
                .stream()
                .map(CardEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Card> findCardsForQuizzByDate(LocalDate date) {
        return repository.findAll()
                .stream()
                .filter(cardEntity -> {
                    if(cardEntity.getLastAnsweredAt() == null) return true;
                    LocalDateTime last = cardEntity.getLastAnsweredAt();
                    LocalDate nextReview = switch(cardEntity.getCategory()) {
                        case "FIRST" -> last.toLocalDate().plusDays(1);
                        case "SECOND" -> last.toLocalDate().plusDays(3);
                        case "THIRD" -> last.toLocalDate().plusDays(7);
                        case "FOURTH" -> last.toLocalDate().plusDays(14);
                        case "FIFTH" -> last.toLocalDate().plusDays(30);
                        case "SIXTH" -> last.toLocalDate().plusDays(60);
                        case "SEVENTH" -> last.toLocalDate().plusDays(90);
                        default -> last.toLocalDate();
                    };
                return !nextReview.isAfter(date);
                })
                .map(CardEntityMapper::toDomain)
                .toList();
    }
}
