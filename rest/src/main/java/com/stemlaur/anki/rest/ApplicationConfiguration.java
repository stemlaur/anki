package com.stemlaur.anki.rest;

import com.stemlaur.anki.application.infrastructure.InMemoryDeckRepository;
import com.stemlaur.anki.domain.catalog.DeckService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    DeckService deckService() {
        return new DeckService(new InMemoryDeckRepository());
    }
}
