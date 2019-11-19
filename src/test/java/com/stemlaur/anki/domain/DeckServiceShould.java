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
public final class DeckServiceShould {
    private DeckService deckService;
    @Mock
    private DeckRepository deckRepository;

    @Before
    public void setUp() {
        this.deckService = new DeckService(this.deckRepository);
    }

    @Test
    public void createAnEmptyDeck() {
        final String id = this.deckService.create("My first deck");
        assertNotNull(id);
        verify(this.deckRepository, times(1))
                .save(any());
    }

    @Test
    public void createDecksWithDifferentId() {
        final String firstDeckId = this.deckService.create("My first deck");
        final String secondDeckId = this.deckService.create("My first deck");
        assertNotEquals(firstDeckId, secondDeckId);
    }

    @Test
    public void removeADeck_when_itExists() {
        final String deckId = this.deckService.create("My first deck");
        this.deckService.remove(deckId);
        verify(this.deckRepository, times(1))
                .delete(any());
    }
}
