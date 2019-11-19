package com.stemlaur.anki.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public final class DeckServiceShould {
    private DeckService deckService;
    @Mock
    private DeckRepository deckRepository;

    @Before
    public void setUp() {
        this.deckService = new DeckService(this.deckRepository);
    }

    @Test
    public void create_an_empty_deck() {
        final String id = this.deckService.create("My first deck");
        assertNotNull(id);
        verify(this.deckRepository, times(1))
                .save(any());
    }

    @Test
    public void create_decks_with_different_id() {
        final String firstDeckId = this.deckService.create("My first deck");
        final String secondDeckId = this.deckService.create("My first deck");
        assertNotEquals(firstDeckId, secondDeckId);
    }

    @Test
    public void remove_a_deck_when_it_exists() {
        final String deckId = this.deckService.create("My first deck");
        this.deckService.remove(deckId);
        verify(this.deckRepository, times(1))
                .delete(any());
    }
}
