/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.stemlaur.anki.application;

import com.stemlaur.anki.application.providers.common.ForwardDomainEventPublisher;
import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.catalog.spi.Decks;
import com.stemlaur.anki.domain.catalog.spi.fake.SimpleDeckIdFactory;
import com.stemlaur.anki.domain.common.spi.DomainEvents;
import com.stemlaur.anki.domain.stats.DeckStatsService;
import com.stemlaur.anki.domain.study.CardProgressService;
import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.SessionIdFactory;
import com.stemlaur.anki.domain.study.api.StudyDeck;
import com.stemlaur.anki.domain.study.spi.CardProgresses;
import com.stemlaur.anki.domain.study.spi.Sessions;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class ApplicationConfiguration {

    @Bean
    static StudyDeck deckStudyService(final Decks decks,
                                      final CardProgresses cardProgresses,
                                      final Sessions sessions,
                                      final DomainEvents domainEvents) {
        return new DeckStudyService(
                new DeckService(decks, new SimpleDeckIdFactory(), domainEvents),
                new CardProgressService(cardProgresses),
                new SessionIdFactory(),
                sessions,
                Clock.systemUTC());
    }

    @Bean
    static DeckService deckService(final Decks decks,
                                   final DomainEvents domainEvents) {
        return new DeckService(decks, new SimpleDeckIdFactory(), domainEvents);
    }

    @Bean
    static CardProgressService cardProgressService(final CardProgresses cardProgresses) {
        return new CardProgressService(cardProgresses);
    }

    @Bean
    static DeckStatsService deckStatsService() {
        return new DeckStatsService();
    }

    @Bean
    DomainEvents forwardDomainEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        return new ForwardDomainEventPublisher(applicationEventPublisher);
    }
}
