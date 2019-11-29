package com.stemlaur.anki.application;

import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.infrastructure.InMemoryDeckRepository;
import com.stemlaur.anki.rest.controllers.RestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RestConfiguration.class)
public class ApplicationConfiguration {
    @Bean
    DeckService deckService() {
        return new DeckService(new InMemoryDeckRepository());
    }
}
