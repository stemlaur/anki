package com.stemlaur.anki.feature.common;

import org.springframework.context.annotation.Bean;
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
}
