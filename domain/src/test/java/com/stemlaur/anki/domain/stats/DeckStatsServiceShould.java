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
package com.stemlaur.anki.domain.stats;

import com.stemlaur.anki.domain.catalog.DeckId;
import com.stemlaur.anki.domain.catalog.api.DeckCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeckStatsServiceShould {
    private final DeckCreated FIRST_DECK_CREATED = new DeckCreated(DeckId.from("first"), "My super deck");
    private final DeckCreated SECOND_DECK_CREATED = new DeckCreated(DeckId.from("second"), "My super deck");

    private DeckStatsService deckStatsService;

    @BeforeEach
    public void setUp() {
        deckStatsService = new DeckStatsService();
    }

    @Test
    public void startWithNoDecks() {
        assertThat(this.deckStatsService.numberOfDecks()).isEqualTo(0);
    }

    @Test
    public void incrementNumberOfDecks() {
        this.deckStatsService.incrementByOne(FIRST_DECK_CREATED);
        assertThat(this.deckStatsService.numberOfDecks()).isEqualTo(1);
        this.deckStatsService.incrementByOne(SECOND_DECK_CREATED);
        assertThat(this.deckStatsService.numberOfDecks()).isEqualTo(2);
    }

    @Test
    public void notCountTwoTimeTheSameDeck() {
        this.deckStatsService.incrementByOne(FIRST_DECK_CREATED);
        assertThat(this.deckStatsService.numberOfDecks()).isEqualTo(1);
        this.deckStatsService.incrementByOne(FIRST_DECK_CREATED);
        assertThat(this.deckStatsService.numberOfDecks()).isEqualTo(1);
    }
}
