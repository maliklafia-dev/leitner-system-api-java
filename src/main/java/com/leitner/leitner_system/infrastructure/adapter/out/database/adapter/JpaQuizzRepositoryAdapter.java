package com.leitner.leitner_system.infrastructure.adapter.out.database.adapter;

import com.leitner.leitner_system.domain.models.Quizz;
import com.leitner.leitner_system.domain.ports.secondary.QuizzRepositoryPort;
import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.QuizzEntity;
import com.leitner.leitner_system.infrastructure.adapter.out.database.mappers.QuizzEntityMapper;
import com.leitner.leitner_system.infrastructure.adapter.out.database.repository.SpringDataQuizzRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaQuizzRepositoryAdapter implements QuizzRepositoryPort {

    private SpringDataQuizzRepository repository;
    public JpaQuizzRepositoryAdapter(SpringDataQuizzRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Quizz> findAll() {
        return repository.findAll()
                .stream()
                .map(QuizzEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Quizz save(Quizz quizz) {
        QuizzEntity quizzEntity = QuizzEntityMapper.toEntity(quizz);
        return QuizzEntityMapper.toDomain(repository.save(quizzEntity));
    }

    @Override
    public Quizz findQuizzById(UUID quizzId) {
        return repository.findById(quizzId)
                .map(QuizzEntityMapper::toDomain)
                .orElse(null);
    }
}
