package com.stemlaur.anki.domain.catalog;

import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.Deck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckShould {

    private static final String QUESTION = "question";
    private static final String A_TITLE = "a title";
    private static final String DECK_ID = "123";
    private Deck deck;

    @Before
    public void setUp() {
        this.deck = new Deck(DECK_ID, A_TITLE);
    }

    @Test(expected = Deck.DeckTitleIsRequired.class)
    public void notCreateDeck_when_titleIsNull() {
        new Deck("an id", null);
    }

    @Test(expected = Deck.DeckTitleIsRequired.class)
    public void notCreateDeck_when_titleIsblank() {
        new Deck("an id", "  ");
    }

    @Test(expected = Deck.DeckIdIsRequired.class)
    public void notCreateDeck_when_idIsNull() {
        new Deck(null, "a title");
    }

    @Test(expected = Deck.DeckIdIsRequired.class)
    public void notCreateDeck_when_idIsBlank() {
        new Deck("  ", "a title");
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
        this.deck.addCard(new CardDetail(QUESTION));
        assertEquals(new CardDetail(QUESTION), this.deck.cards().get(0).detail());
    }

    @Test(expected = NullPointerException.class)
    public void throwAnException_when_addedCardIsNull() {
        this.deck.addCard(null);
    }

    @Test
    public void addTwoCardsWithDifferentIds() {
        this.deck.addCard(new CardDetail("question 1"));
        this.deck.addCard(new CardDetail("question 2"));
        assertNotEquals(this.deck.cards().get(0).id(), this.deck.cards().get(1).id());
    }

    @Test
    public void removeCard_when_itExits() {
        int id = this.deck.addCard(new CardDetail("question 1"));
        assertFalse(this.deck.cards().isEmpty());
        this.deck.removeCard(id);
        assertTrue(this.deck.cards().isEmpty());
    }

    @Test
    public void doNothing_when_removingCardWhichDoesNotExist() {
        int id = this.deck.addCard(new CardDetail("question 1"));
        assertEquals(1, this.deck.cards().size());
        this.deck.removeCard(id + 777);
        assertEquals(1, this.deck.cards().size());
    }

    @Test
    public void haveUniqueCardIds() {
        int id1 = this.deck.addCard(new CardDetail("question 1"));
        this.deck.removeCard(id1);
        int id2 = this.deck.addCard(new CardDetail("question 2"));
        assertNotEquals(id1, id2);
    }
}