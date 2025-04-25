package com.leitner.leitner_system.infrastructure.out.database.adapter;

import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.infrastructure.adapter.out.database.adapter.JpaCardRepositoryAdapter;
import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.CardEntity;
import com.leitner.leitner_system.infrastructure.adapter.out.database.mappers.CardEntityMapper;
import com.leitner.leitner_system.infrastructure.adapter.out.database.repository.SpringDataCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaCardRepositoryAdapterTest {

    @Mock
    private SpringDataCardRepository springDataCardRepository;

    private JpaCardRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new JpaCardRepositoryAdapter(springDataCardRepository);
    }

    @Test
    void shouldSaveCard() {
        Card card = new Card("Q", "A", "tag");
        CardEntity entity = CardEntityMapper.toEntity(card);

        when(springDataCardRepository.save(any(CardEntity.class))).thenReturn(entity);

        Card result = adapter.save(card);

        assertNotNull(result);
        assertEquals(card.getQuestion(), result.getQuestion());
        verify(springDataCardRepository).save(any(CardEntity.class));
    }

    @Test
    void shouldReturnCardById() {
        Card card = new Card("Q", "A", "tag");
        CardEntity entity = CardEntityMapper.toEntity(card);
        UUID id = entity.getId();

        when(springDataCardRepository.findById(id)).thenReturn(java.util.Optional.of(entity));

        Card result = adapter.findCardById(id);

        assertNotNull(result);
        assertEquals(card.getQuestion(), result.getQuestion());
    }

    @Test
    void shouldReturnAllCards() {
        CardEntity entity = CardEntityMapper.toEntity(new Card("Q", "A", "tag"));

        when(springDataCardRepository.findAll()).thenReturn(List.of(entity));

        List<Card> result = adapter.findAllCards();

        assertEquals(1, result.size());
        assertEquals("Q", result.get(0).getQuestion());
    }

    @Test
    void shouldReturnCardsByTags() {
        List<CardEntity> entities = List.of(CardEntityMapper.toEntity(new Card("Q1", "A1", "tag1")));
        when(springDataCardRepository.findByTagIn(List.of("tag1"))).thenReturn(entities);

        List<Card> cards = adapter.findCardsByTag(List.of("tag1"));

        assertEquals(1, cards.size());
        assertEquals("tag1", cards.get(0).getTag());
    }
}
