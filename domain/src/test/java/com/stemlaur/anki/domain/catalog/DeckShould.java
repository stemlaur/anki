package com.stemlaur.anki.domain.catalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeckShould {

    private static final String QUESTION = "question";
    private static final String A_TITLE = "a title";
    private static final String DECK_ID = "dbf2a115-ac6c-4873-9d7a-65c8d4ab704c";
    private static final String ANOTHER_DECK_ID = "38327e20-b7d8-40c5-8bf7-eeaacd002367";
    private Deck deck;

    @BeforeEach
    public void setUp() {
        this.deck = new Deck(new DeckId(DECK_ID), new DeckTitle(A_TITLE));
    }

    @Test
    public void notCreateDeck_when_titleIsNull() {
        assertThrows(DeckTitleIsRequired.class,
                () -> new Deck(new DeckId("an id"), new DeckTitle(null)));
    }

    @Test
    public void notCreateDeck_when_titleIsblank() {
        assertThrows(DeckTitleIsRequired.class,
                () -> new Deck(new DeckId("an id"), new DeckTitle("  ")));
    }

    @Test
    public void notCreateDeck_when_idIsNull() {
        assertThrows(DeckIdIsRequired.class,
                () -> new Deck(new DeckId(null), new DeckTitle("a title")));
    }

    @Test
    public void notCreateDeck_when_idIsBlank() {

        assertThrows(DeckIdIsRequired.class,
                () -> new Deck(new DeckId("  "), new DeckTitle("a title")));
    }

    @Test
    public void createDeckWithEmptyListOfCard() {
        assertTrue(this.deck.cards().isEmpty());
    }

    @Test
    public void createDeckWithTitle() {
        assertEquals(A_TITLE, this.deck.title());
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
        final Deck firstDeck = new Deck(new DeckId(DECK_ID), new DeckTitle("A deck title"));
        final Deck secondDeck = new Deck(new DeckId(DECK_ID), new DeckTitle("Another deck title"));
        final Deck differentDeck = new Deck(new DeckId(ANOTHER_DECK_ID), new DeckTitle("Another deck title"));

        assertEquals(firstDeck, secondDeck);
        assertNotEquals(firstDeck, differentDeck);
    }
}
