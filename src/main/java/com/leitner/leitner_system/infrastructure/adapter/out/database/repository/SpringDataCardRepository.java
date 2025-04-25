package com.leitner.leitner_system.infrastructure.adapter.out.database.repository;

import com.leitner.leitner_system.infrastructure.adapter.out.database.entities.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataCardRepository extends JpaRepository<CardEntity, UUID> {
    List<CardEntity> findByTagIn(Collection<String> tags);
}
