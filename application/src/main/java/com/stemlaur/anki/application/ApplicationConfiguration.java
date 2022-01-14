package com.stemlaur.anki.application;

import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.catalog.spi.Decks;
import com.stemlaur.anki.domain.catalog.spi.fake.SimpleDeckIdFactory;
import com.stemlaur.anki.domain.common.spi.fake.FakeDomainEvents;
import com.stemlaur.anki.domain.study.CardProgressService;
import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.SessionIdFactory;
import com.stemlaur.anki.domain.study.api.StudyDeck;
import com.stemlaur.anki.domain.study.spi.CardProgresses;
import com.stemlaur.anki.domain.study.spi.Sessions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class ApplicationConfiguration {

    @Bean
    static StudyDeck deckStudyService(final Decks decks,
                                      final CardProgresses cardProgresses,
                                      final Sessions sessions) {
        return new DeckStudyService(
                new DeckService(decks, new SimpleDeckIdFactory(), new FakeDomainEvents()),
                new CardProgressService(cardProgresses),
                new SessionIdFactory(),
                sessions,
                Clock.systemUTC());
    }

    @Bean
    static DeckService deckService(final Decks decks) {
        return new DeckService(decks, new SimpleDeckIdFactory(), new FakeDomainEvents());
    }

    @Bean
    static CardProgressService cardProgressService(final CardProgresses cardProgresses) {
        return new CardProgressService(cardProgresses);
    }
}
