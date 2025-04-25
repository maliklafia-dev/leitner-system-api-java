package com.leitner.leitner_system.infrastructure.adapter.out.database.repository;

import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.QuizzEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataQuizzRepository extends JpaRepository<QuizzEntity, UUID> {
}
