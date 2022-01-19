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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckShould {

    private static final DeckId DECK_ID = DeckId.from("any id");
    private static final DeckId ANOTHER_DECK_ID = DeckId.from("any other id");
    private static final String QUESTION = "question";
    private static final String A_TITLE = "a title";
    private Deck deck;

    @BeforeEach
    public void setUp() {
        this.deck = new Deck(DECK_ID, new DeckTitle(A_TITLE));
    }

    @Test
    public void notCreateDeck_when_titleIsNull() {
        assertThrows(DeckTitleIsRequired.class,
                () -> new Deck(DECK_ID, null));
    }

    @Test
    public void notCreateDeck_when_idIsNull() {
        assertThrows(DeckIdIsRequired.class,
                () -> new Deck(null, new DeckTitle("a title")));
    }

    @Test
    public void createDeckWithEmptyListOfCard() {
        assertTrue(this.deck.cards().isEmpty());
    }

    @Test
    public void createDeckWithTitle() {
        assertEquals(A_TITLE, this.deck.titleString());
    }

    @Test
    public void addACardToAnEmptyDeck() {
        this.deck.addCard(new CardDetail(QUESTION, "The answer"));
        assertEquals(new CardDetail(QUESTION, "The answer"), this.deck.cards().get(0).detail());
    }

    @Test
    public void throwAnException_when_addedCardIsNull() {
        assertThrows(NullPointerException.class,
                () -> this.deck.addCard(null));
    }

    @Test
    public void addTwoCardsWithDifferentIds() {
        this.deck.addCard(new CardDetail("question 1", "The answer"));
        this.deck.addCard(new CardDetail("question 2", "The answer"));

        assertNotEquals(this.deck.cards().get(0).id(), this.deck.cards().get(1).id());
    }

    @Test
    public void removeCard_when_itExits() {
        int id = this.deck.addCard(new CardDetail("question 1", "The answer"));
        assertFalse(this.deck.cards().isEmpty());
        this.deck.removeCard(id);
        assertTrue(this.deck.cards().isEmpty());
    }

    @Test
    public void doNothing_when_removingCardWhichDoesNotExist() {
        int id = this.deck.addCard(new CardDetail("question 1", "The answer"));
        assertEquals(1, this.deck.cards().size());
        this.deck.removeCard(id + 777);
        assertEquals(1, this.deck.cards().size());
    }

    @Test
    public void haveUniqueCardIds() {
        int id1 = this.deck.addCard(new CardDetail("question 1", "The answer"));
        this.deck.removeCard(id1);
        int id2 = this.deck.addCard(new CardDetail("question 2", "The answer"));
        assertNotEquals(id1, id2);
    }

    @Test
    public void beIdentifiedByItsId() {
        final Deck firstDeck = new Deck(DECK_ID, new DeckTitle("A deck title"));
        final Deck secondDeck = new Deck(DECK_ID, new DeckTitle("Another deck title"));
        final Deck differentDeck = new Deck(ANOTHER_DECK_ID, new DeckTitle("Another deck title"));

        assertEquals(firstDeck, secondDeck);
        assertNotEquals(firstDeck, differentDeck);
        assertEquals(firstDeck.hashCode(), secondDeck.hashCode());
        assertNotEquals(firstDeck.hashCode(), differentDeck.hashCode());
    }
}
