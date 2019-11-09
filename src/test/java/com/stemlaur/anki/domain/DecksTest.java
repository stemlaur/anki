package com.stemlaur.anki.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public final class DecksTest {

    private Decks decks;

    @Before
    public void setUp() {
        this.decks = new Decks();
    }

    @Test
    public void should_create_an_empty_deck() {
        final Deck actualDeck = decks.create("My first deck");
        final String expectedTitle = "My first deck";
        assertEquals(expectedTitle, actualDeck.title());
        assertNotNull(actualDeck.id());
    }

    @Test
    public void should_create_decks_with_different_id() {
        final Deck firstDeck = decks.create("My first deck");
        final Deck secondDeck = decks.create("My first deck");
        assertNotEquals(firstDeck.id(), secondDeck.id());
    }
}
