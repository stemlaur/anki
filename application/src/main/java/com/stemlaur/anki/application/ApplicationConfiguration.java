package com.stemlaur.anki.application;

import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.catalog.Decks;
import com.stemlaur.anki.domain.common.Clock;
import com.stemlaur.anki.domain.study.CardProgressService;
import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.SessionIdFactory;
import com.stemlaur.anki.infrastructure.InMemoryCardProgresses;
import com.stemlaur.anki.infrastructure.InMemoryDecks;
import com.stemlaur.anki.infrastructure.InMemorySessions;
import com.stemlaur.anki.rest.controllers.RestConfiguration;
import com.stemlaur.anki.infrastructure.sql.SqlPersistenceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RestConfiguration.class, SqlPersistenceConfiguration.class})
class ApplicationConfiguration {

    @Bean
    DeckService deckService(final Decks decks) {
        return new DeckService(decks);
    }

    @Bean
    CardProgressService cardProgressService() {
        return new CardProgressService(new InMemoryCardProgresses());
    }

    @Bean
    DeckStudyService deckStudyService(final DeckService deckService,
                                      final CardProgressService cardProgressService) {
        return new DeckStudyService(
                deckService,
                cardProgressService,
                new SessionIdFactory(),
                new InMemorySessions(),
                new Clock());
    }
}
