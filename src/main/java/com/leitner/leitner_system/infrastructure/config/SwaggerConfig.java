package com.leitner.leitner_system.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Leitner System API")
                        .version("1.0.0")
                        .description("This API allows managing flashcards using the Leitner method.")
                        .contact(new Contact()
                                .name("Malik Lafia")
                                .email("maliksero.lafia@gmail.com")));

    }
}
