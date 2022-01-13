package com.stemlaur.anki.application;

import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.catalog.fake.InMemoryDecks;
import com.stemlaur.anki.domain.common.Clock;
import com.stemlaur.anki.domain.study.CardProgressService;
import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.SessionIdFactory;
import com.stemlaur.anki.domain.study.api.StudyDeck;
import com.stemlaur.anki.domain.study.fake.InMemoryCardProgresses;
import com.stemlaur.anki.domain.study.fake.InMemorySessions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApplicationConfiguration {
    @Bean
    DeckService deckService() {
        return new DeckService(new InMemoryDecks());
    }

    @Bean
    CardProgressService cardProgressService() {
        return new CardProgressService(new InMemoryCardProgresses());
    }

    @Bean
    StudyDeck deckStudyService(final DeckService deckService,
                               final CardProgressService cardProgressService) {
        return new DeckStudyService(
                deckService,
                cardProgressService,
                new SessionIdFactory(),
                new InMemorySessions(),
                new Clock());
    }
}
