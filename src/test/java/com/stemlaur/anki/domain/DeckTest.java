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

}