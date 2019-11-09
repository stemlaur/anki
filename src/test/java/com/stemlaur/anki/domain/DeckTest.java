package com.stemlaur.anki.domain;

import com.stemlaur.anki.domain.common.Tuple;
import com.stemlaur.anki.domain.common.TupleWithMultipleEvents;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {
    private static final String QUESTION_1 = "question 1";
    private static final String QUESTION_2 = "question 2";
    private static final String A_TITLE = "a title";
    private Deck deck;

    @Before
    public void setUp() throws Exception {
        this.deck = Deck.create("123", A_TITLE).getAggregate();
    }

    @Test(expected = Deck.DeckTitleIsRequired.class)
    public void should_notcreate_deck_when_title_is_null() {
        Deck.create("an id", null);
    }

    @Test(expected = Deck.DeckTitleIsRequired.class)
    public void should_notcreate_deck_when_title_is_blank() {
        Deck.create("an id", "  ");
    }

    @Test(expected = Deck.DeckIdIsRequired.class)
    public void should_notcreate_deck_when_id_is_null() {
        Deck.create(null, A_TITLE);
    }

    @Test(expected = Deck.DeckIdIsRequired.class)
    public void should_notcreate_deck_when_id_is_blank() {
        Deck.create("  ", A_TITLE);
    }

    @Test
    public void should_create_deck_with_empty_list_of_card() {
        assertTrue(this.deck.cards().isEmpty());
    }

    @Test
    public void should_return_DeckCreated() {
        final Tuple<DeckCreated, Deck> deckTuple = Deck.create("123", A_TITLE);
        assertNotNull(deckTuple.getEvent());
        assertEquals(new DeckCreated("123", A_TITLE), deckTuple.getEvent());
    }

    @Test
    public void should_add_a_card_to_an_empty_deck() {
        final Tuple<CardAdded, Deck> deckTuple = this.deck.addCard(new CardDetail(QUESTION_1));
        assertEquals(new CardDetail(QUESTION_1), this.deck.cards().get(0).detail());
        assertEquals(new CardAdded("123", this.deck.cards().get(0).id(), QUESTION_1), deckTuple.getEvent());
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_an_exception_when_added_card_is_null() {
        this.deck.addCard(null);
    }

    @Test
    public void should_add_two_cards_with_different_ids() {
        this.deck.addCard(new CardDetail(QUESTION_1));
        this.deck.addCard(new CardDetail(QUESTION_2));
        assertNotEquals(this.deck.cards().get(0).id(), this.deck.cards().get(1).id());
    }

    @Test
    public void should_remove_card_when_it_exits() {
        final Tuple<CardAdded, Deck> deckTuple = this.deck.addCard(new CardDetail(QUESTION_1));
        assertFalse(this.deck.cards().isEmpty());
        this.deck.removeCard(deckTuple.getAggregate().cards().get(0).id());
        assertTrue(this.deck.cards().isEmpty());
    }

    @Test
    public void should_do_nothing_when_removing_card_which_does_not_exist() {
        final Tuple<CardAdded, Deck> deckTuple = this.deck.addCard(new CardDetail(QUESTION_1));
        assertEquals(1, this.deck.cards().size());
        this.deck.removeCard(deckTuple.getAggregate().cards().get(0).id() + 777);
        assertEquals(1, this.deck.cards().size());
    }

    @Test
    public void should_have_unique_card_ids() {
        final Tuple<CardAdded, Deck> deckTuple1 = this.deck.addCard(new CardDetail(QUESTION_1));
        final int id1 = deckTuple1.getAggregate().cards().get(0).id();
        this.deck.removeCard(id1);
        final Tuple<CardAdded, Deck> deckTuple2 = this.deck.addCard(new CardDetail(QUESTION_2));
        final int id2 = deckTuple2.getAggregate().cards().get(0).id();
        assertNotEquals(id1, id2);
    }
}