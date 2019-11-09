package com.stemlaur.anki.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {

    public static final String QUESTION = "question";
    private Deck deck;

    @Before
    public void setUp() throws Exception {
        this.deck = new Deck("123", "a title");
    }

    @Test(expected = Deck.DeckTitleIsRequired.class)
    public void should_notcreate_deck_when_title_is_null() {
        new Deck("an id", null);
    }

    @Test(expected = Deck.DeckTitleIsRequired.class)
    public void should_notcreate_deck_when_title_is_blank() {
        new Deck("an id", "  ");
    }

    @Test(expected = Deck.DeckIdIsRequired.class)
    public void should_notcreate_deck_when_id_is_null() {
        new Deck(null, "a title");
    }

    @Test(expected = Deck.DeckIdIsRequired.class)
    public void should_notcreate_deck_when_id_is_blank() {
        new Deck("  ", "a title");
    }

    @Test
    public void should_create_deck_with_empty_list_of_card() {
        assertTrue(this.deck.cards().isEmpty());
    }

    @Test
    public void should_add_a_card_to_an_empty_deck() {
        this.deck.addCard(new CardDetail(QUESTION));
        assertEquals(new CardDetail(QUESTION), this.deck.cards().get(0).detail());
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_an_exception_when_added_card_is_null() {
        this.deck.addCard(null);
    }

    @Test
    public void should_add_two_cards_with_different_ids() {
        this.deck.addCard(new CardDetail("question 1"));
        this.deck.addCard(new CardDetail("question 2"));
        assertNotEquals(this.deck.cards().get(0).id(), this.deck.cards().get(1).id());
    }
}