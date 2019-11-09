package com.stemlaur.anki.domain;

import org.junit.Test;

public class DeckTest {

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
        assert (new Deck("123", "a title").cards().isEmpty());
    }

}