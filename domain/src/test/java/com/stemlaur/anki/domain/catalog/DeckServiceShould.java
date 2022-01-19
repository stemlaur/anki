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
package com.stemlaur.anki.domain.catalog;

import com.stemlaur.anki.domain.catalog.spi.Decks;
import com.stemlaur.anki.domain.catalog.spi.fake.InMemoryDecks;
import com.stemlaur.anki.domain.common.spi.fake.FakeDomainEvents;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class DeckServiceShould {
    private static final DeckId DECK_ID = DeckId.from("any id");
    private static final String NON_EXISTING_DECK_ID = "ANYID";
    private static final String DECK_TITLE = "Neuro-fun";
    private static final String ANOTHER_DECK_TITLE = "Test deck";
    private static final String A_QUESTION = "What is the name of the standard used to order the human brain anatomical regions ?";
    private static final String A_ANSWER = "The neuroanatomy hierarchies.";

    private final FakeDomainEvents fakeDomainEvents = new FakeDomainEvents();
    private DumbDeckIdFactory dummyDeckIdFactory;
    private DeckService deckService;
    private Decks decks;

    @BeforeEach
    public void setUp() {
        this.dummyDeckIdFactory = new DumbDeckIdFactory("any id");
        this.decks = new InMemoryDecks();
        this.deckService = new DeckService(
                this.decks,
                this.dummyDeckIdFactory,
                this.fakeDomainEvents);
    }

    @AfterEach
    public void tearDown() {
        this.fakeDomainEvents.clear();
    }

    @Test
    public void createAnEmptyDeck() {
        final String id = this.deckService.create(DECK_TITLE);

        assertNotNull(id);

        assertThat(this.decks.findAll()).isNotEmpty();
    }

    @Test
    public void createDecksWithDifferentId() {
        final String firstDeckId = this.deckService.create(DECK_TITLE);

        dummyDeckIdFactory.changedFixedId("another id");

        final String secondDeckId = this.deckService.create(ANOTHER_DECK_TITLE);
        assertNotEquals(firstDeckId, secondDeckId);
    }

    @Test
    public void publishDeckCreated() {
        this.deckService.create(DECK_TITLE);

        assertThat(fakeDomainEvents.getEvents())
                .containsExactly(new DeckCreated(DeckId.from("any id"), DECK_TITLE));
    }

    @Test
    public void removeADeck_when_itExists() {
        this.deckService.create("My super deck");

        assertThat(this.decks.findAll()).isNotEmpty();

        this.deckService.remove(DECK_ID.getValue());

        assertThat(this.decks.findAll()).isEmpty();
    }

    @Test
    public void throwAnException_when_deckDoesNotExist() {
        assertThrows(DeckDoesNotExist.class,
                () -> this.deckService.remove(NON_EXISTING_DECK_ID));
    }

    @Test
    public void throwAnException_when_addingCardToANonExistingDeck() {
        assertThrows(DeckDoesNotExist.class,
                () -> this.deckService.addCard(NON_EXISTING_DECK_ID, new CardDetail(A_QUESTION, A_ANSWER)));
    }

    @Test
    public void addACard_when_deckExists() {
        this.deckService.create("My super deck");

        this.deckService.addCard(DECK_ID.getValue(), new CardDetail(A_QUESTION, A_ANSWER));

        assertThat(this.decks.find(DECK_ID.getValue()).orElseThrow().cards())
                .hasSize(1);
    }

    @Test
    public void publishCardAdded() {
        this.deckService.create("My super deck");
        fakeDomainEvents.clear();

        this.deckService.addCard(DECK_ID.getValue(), new CardDetail(A_QUESTION, A_ANSWER));

        assertThat(fakeDomainEvents.getEvents())
                .containsExactly(new CardAdded(DECK_ID, 1, A_QUESTION, A_ANSWER));
    }

    @Test
    public void findAllDecks() {
        this.deckService.create("My super deck");

        assertThat(this.deckService.all()).hasSize(1);
    }
}
