package com.leitner.leitner_system.infrastructure.config;


import com.leitner.leitner_system.domain.ports.secondary.CardRepositoryPort;
import com.leitner.leitner_system.domain.ports.secondary.QuizzRepositoryPort;
import com.leitner.leitner_system.domain.service.CardService;
import com.leitner.leitner_system.domain.service.QuizzService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CardService cardService(CardRepositoryPort cardRepo) {
        return new CardService(cardRepo);
    }

    @Bean
    public QuizzService quizzService(QuizzRepositoryPort quizzRepo) {
        return new QuizzService(quizzRepo);
    }
}
