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
package com.stemlaur.anki.domain.catalog.fake;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckId;
import com.stemlaur.anki.domain.catalog.DeckTitle;
import com.stemlaur.anki.domain.catalog.spi.fake.InMemoryDecks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryDecksShould {
    private static final DeckId DECK_ID = DeckId.from("any id");
    private static final DeckId ANOTHER_DECK_ID = DeckId.from("any other id");

    private InMemoryDecks inMemoryDecks;

    @BeforeEach
    public void setUp() {
        this.inMemoryDecks = new InMemoryDecks();
    }

    @Test
    public void addADeck() {
        assertTrue(this.inMemoryDecks.find(DECK_ID.getValue()).isEmpty());
        this.inMemoryDecks.save(new Deck(DECK_ID, new DeckTitle("title")));
        assertTrue(this.inMemoryDecks.find(DECK_ID.getValue()).isPresent());
    }

    @Test
    public void overrideDeckWhenAlreadyExist() {
        this.inMemoryDecks.save(new Deck(DECK_ID, new DeckTitle("title")));
        this.inMemoryDecks.save(new Deck(DECK_ID, new DeckTitle("title")));
        assertEquals(1, this.inMemoryDecks.findAll().size());
    }

    @Test
    public void deleteADeck() {
        this.inMemoryDecks.save(new Deck(DECK_ID, new DeckTitle("title")));
        assertTrue(this.inMemoryDecks.find(DECK_ID.getValue()).isPresent());

        this.inMemoryDecks.delete(DECK_ID.getValue());
        assertTrue(this.inMemoryDecks.find(DECK_ID.getValue()).isEmpty());
    }

    @Test
    public void findAllDecks() {
        this.inMemoryDecks.save(new Deck(DECK_ID, new DeckTitle("title")));
        this.inMemoryDecks.save(new Deck(ANOTHER_DECK_ID, new DeckTitle("title")));

        assertEquals(2, this.inMemoryDecks.findAll().size());
    }
}
