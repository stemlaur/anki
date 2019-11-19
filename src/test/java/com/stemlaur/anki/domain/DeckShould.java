package com.stemlaur.anki.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckShould {
    private static final String QUESTION_1 = "question 1";
    private static final String QUESTION_2 = "question 2";
    private static final String A_TITLE = "a title";
    private Deck deck;

    @Before
    public void setUp() throws Exception {
        this.deck = Deck.create("123", A_TITLE);
    }

    @Test(expected = Deck.DeckTitleIsRequired.class)
    public void notcreate_deck_when_title_is_null() {
        Deck.create("an id", null);
    }

    @Test(expected = Deck.DeckTitleIsRequired.class)
    public void notcreate_deck_when_title_is_blank() {
        Deck.create("an id", "  ");
    }

    @Test(expected = Deck.DeckIdIsRequired.class)
    public void notcreate_deck_when_id_is_null() {
        Deck.create(null, A_TITLE);
    }

    @Test(expected = Deck.DeckIdIsRequired.class)
    public void notcreate_deck_when_id_is_blank() {
        Deck.create("  ", A_TITLE);
    }

    @Test
    public void create_deck_with_empty_list_of_card() {
        assertTrue(this.deck.cards().isEmpty());
    }

    @Test
    public void add_a_card_to_an_empty_deck() {
        this.deck.addCard(new CardDetail(QUESTION_1));
        assertEquals(new CardDetail(QUESTION_1), this.deck.cards().get(0).detail());
    }

    @Test(expected = NullPointerException.class)
    public void throw_an_exception_when_added_card_is_null() {
        this.deck.addCard(null);
    }

    @Test
    public void add_two_cards_with_different_ids() {
        this.deck.addCard(new CardDetail(QUESTION_1));
        this.deck.addCard(new CardDetail(QUESTION_2));
        assertNotEquals(this.deck.cards().get(0).id(), this.deck.cards().get(1).id());
    }

    @Test
    public void remove_card_when_it_exits() {
        this.deck.addCard(new CardDetail(QUESTION_1));
        assertFalse(this.deck.cards().isEmpty());
        this.deck.removeCard(deck.cards().get(0).id());
        assertTrue(this.deck.cards().isEmpty());

    }

    @Test
    public void do_nothing_when_removing_card_which_does_not_exist() {
        this.deck.addCard(new CardDetail(QUESTION_1));
        assertEquals(1, this.deck.cards().size());
        this.deck.removeCard(this.deck.cards().get(0).id() + 777);
        assertEquals(1, this.deck.cards().size());
    }

    @Test
    public void have_unique_card_ids() {
        this.deck.addCard(new CardDetail(QUESTION_1));
        final int id1 = this.deck.cards().get(0).id();
        this.deck.removeCard(id1);
        this.deck.addCard(new CardDetail(QUESTION_2));
        final int id2 = this.deck.cards().get(0).id();
        assertNotEquals(id1, id2);
    }
}