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
