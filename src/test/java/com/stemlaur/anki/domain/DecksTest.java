package com.stemlaur.anki.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public final class DecksTest {
    private Decks decks;
    @Mock
    private DeckRepository deckRepository;

    @Before
    public void setUp() {
        this.decks = new Decks(this.deckRepository);
    }

    @Test
    public void should_create_an_empty_deck() {
        final String id = this.decks.create("My first deck");
        assertNotNull(id);
        verify(this.deckRepository, times(1))
                .save(any());
    }

    @Test
    public void should_create_decks_with_different_id() {
        final String firstDeckId = this.decks.create("My first deck");
        final String secondDeckId = this.decks.create("My first deck");
        assertNotEquals(firstDeckId, secondDeckId);
    }

    @Test
    public void should_remove_a_deck_when_it_exists() {
        final String deckId = this.decks.create("My first deck");
        this.decks.remove(deckId);
        verify(this.deckRepository, times(1))
                .delete(any());
    }
}
