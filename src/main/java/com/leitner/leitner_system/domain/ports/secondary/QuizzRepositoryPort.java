package com.leitner.leitner_system.domain.ports.secondary;

import com.leitner.leitner_system.domain.models.Quizz;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface QuizzRepositoryPort {
    List<Quizz> findAll();
    Quizz save(Quizz quizz);
    Quizz findQuizzById(UUID quizzId);
}
