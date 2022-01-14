package com.stemlaur.anki.feature.common;

import com.stemlaur.anki.domain.catalog.spi.Decks;
import com.stemlaur.anki.domain.catalog.spi.fake.InMemoryDecks;
import com.stemlaur.anki.domain.study.spi.CardProgresses;
import com.stemlaur.anki.domain.study.spi.Sessions;
import com.stemlaur.anki.domain.study.spi.fake.InMemoryCardProgresses;
import com.stemlaur.anki.domain.study.spi.fake.InMemorySessions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;

public class TestConfiguration {

    @Bean
    World world() {
        return new World();
    }

    @Bean
    DeckHttpClient deckHttpClient(final MockMvc mockMvc) {
        return new DeckHttpClient(mockMvc);
    }

    @Bean
    StudyHttpClient studyHttpClient(final MockMvc mockMvc) {
        return new StudyHttpClient(mockMvc);
    }

    @Primary
    @Bean
    Decks inMemorySqlDecks() {
        return new InMemoryDecks();
    }

    @Primary
    @Bean
    CardProgresses inMemoryCardProgresses() {
        return new InMemoryCardProgresses();
    }

    @Primary
    @Bean
    Sessions inMemorySessions() {
        return new InMemorySessions();
    }
}
