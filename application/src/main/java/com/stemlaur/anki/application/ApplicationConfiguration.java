package com.stemlaur.anki.application;

import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.common.Clock;
import com.stemlaur.anki.domain.study.CardProgressService;
import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.SessionIdFactory;
import com.stemlaur.anki.infrastructure.InMemoryCardProgressRepository;
import com.stemlaur.anki.infrastructure.InMemoryDeckRepository;
import com.stemlaur.anki.infrastructure.InMemorySessionRepository;
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

    @Bean
    CardProgressService cardProgressService() {
        return new CardProgressService(new InMemoryCardProgressRepository());
    }

    @Bean
    DeckStudyService deckStudyService(final DeckService deckService,
                                      final CardProgressService cardProgressService) {
        return new DeckStudyService(
                deckService,
                cardProgressService,
                new SessionIdFactory(),
                new InMemorySessionRepository(),
                new Clock());
    }
}
