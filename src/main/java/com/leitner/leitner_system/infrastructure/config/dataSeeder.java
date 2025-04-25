package com.leitner.leitner_system.infrastructure.config;


import com.github.javafaker.Faker;
import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.models.Quizz;
import com.leitner.leitner_system.domain.ports.secondary.CardRepositoryPort;
import com.leitner.leitner_system.domain.ports.secondary.QuizzRepositoryPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration
public class dataSeeder {

    @Bean
    CommandLineRunner seedData(CardRepositoryPort cardRepo, QuizzRepositoryPort quizzRepo) {
        return args -> {
            Faker faker = new Faker();
            UUID userId = UUID.randomUUID();
            List<Card> cards = new ArrayList<>();
            int n = 10;

            //Génération aléatoire de n cartes;
            for (int i = 0; i < n; i++) {
                Card card = new Card(
                  UUID.randomUUID(),
                  faker.educator().course(),
                        faker.lorem().sentence(3),
                        faker.book().genre(),
                        "FIRST",
                        LocalDateTime.now(),
                        null,
                        LocalDateTime.now()
                );
                cardRepo.save(card);
                cards.add(card);
            }
            // Crée un quizz avec ces cartes
            Quizz quizz = new Quizz(userId, cards.subList(0, 3));
            quizz.setId(UUID.randomUUID());
            quizz.start();
            quizz.recordAnswer(cards.get(0).getId(), true);
            quizz.recordAnswer(cards.get(1).getId(), false);

            quizzRepo.save(quizz);

            System.out.println("Données Faker insérées !");
        };
    }
}
