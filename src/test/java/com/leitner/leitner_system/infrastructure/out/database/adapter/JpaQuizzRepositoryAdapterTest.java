package com.leitner.leitner_system.infrastructure.out.database.adapter;

import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.models.Quizz;
import com.leitner.leitner_system.infrastructure.adapter.out.database.adapter.JpaQuizzRepositoryAdapter;
import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.QuizzEntity;
import com.leitner.leitner_system.infrastructure.adapter.out.database.mappers.QuizzEntityMapper;
import com.leitner.leitner_system.infrastructure.adapter.out.database.repository.SpringDataQuizzRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaQuizzRepositoryAdapterTest {

    @Mock
    private SpringDataQuizzRepository springDataQuizzRepository;

    private JpaQuizzRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new JpaQuizzRepositoryAdapter(springDataQuizzRepository);
    }

    @Test
    void shouldSaveQuizz() {
        Quizz quizz = new Quizz(UUID.randomUUID(), List.of(new Card("Q", "A", "tag")));
        QuizzEntity entity = QuizzEntityMapper.toEntity(quizz);

        when(springDataQuizzRepository.save(any(QuizzEntity.class))).thenReturn(entity);

        Quizz result = adapter.save(quizz);

        assertNotNull(result);
        assertEquals(quizz.getUserId(), result.getUserId());
        verify(springDataQuizzRepository).save(any(QuizzEntity.class));
    }

    @Test
    void shouldFindAllQuizz() {
        QuizzEntity entity = QuizzEntityMapper.toEntity(new Quizz(UUID.randomUUID(), List.of(new Card("Q", "A", "tag"))));
        when(springDataQuizzRepository.findAll()).thenReturn(List.of(entity));

        List<Quizz> result = adapter.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindQuizzById() {
        Quizz quizz = new Quizz(UUID.randomUUID(), List.of(new Card("Q", "A", "tag")));
        QuizzEntity entity = QuizzEntityMapper.toEntity(quizz);
        UUID id = entity.getId();

        when(springDataQuizzRepository.findById(id)).thenReturn(Optional.of(entity));

        Quizz result = adapter.findQuizzById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldReturnNullIfQuizzNotFound() {
        UUID id = UUID.randomUUID();

        when(springDataQuizzRepository.findById(id)).thenReturn(Optional.empty());

        Quizz result = adapter.findQuizzById(id);

        assertNull(result);
    }
}
