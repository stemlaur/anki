package com.stemlaur.anki.feature.common;

import com.stemlaur.anki.domain.catalog.Decks;
import com.stemlaur.anki.domain.catalog.fake.InMemoryDecks;
import com.stemlaur.anki.domain.study.CardProgresses;
import com.stemlaur.anki.domain.study.Sessions;
import com.stemlaur.anki.domain.study.fake.InMemoryCardProgresses;
import com.stemlaur.anki.domain.study.fake.InMemorySessions;
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
