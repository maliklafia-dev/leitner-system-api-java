package com.leitner.leitner_system.infrastructure.out.database.repository;

import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.infrastructure.adapter.out.database.repository.SpringDataCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@DataJpaTest
public class SpringDataCardRepositoryTest {
    private JpaRepository<Card, UUID> cardRepository;

    @Autowired
    private SpringDataCardRepository repository;
}
