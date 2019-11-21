package com.stemlaur.anki.domain.catalog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public final class DeckServiceShould {
    private static final String DECK_ID = "12341234";
    private static final String NON_EXISTING_DECK_ID = "ANYID";
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
        when(this.deckRepository.findDeckById(DECK_ID)).thenReturn(Optional.of(new Deck(DECK_ID, "The deck")));
        this.deckService.remove(DECK_ID);
        verify(this.deckRepository, times(1)).delete(any());
    }

    @Test(expected = DeckService.DeckDoesNotExist.class)
    public void throwAnException_when_deckDoesNotExist() {
        when(deckRepository.findDeckById(NON_EXISTING_DECK_ID)).thenReturn(empty());
        this.deckService.remove(NON_EXISTING_DECK_ID);
    }

    @Test(expected = DeckService.DeckDoesNotExist.class)
    public void throwAnException_when_addingCardToANonExistingDeck() {
        when(deckRepository.findDeckById(NON_EXISTING_DECK_ID)).thenReturn(empty());
        this.deckService.addCard(NON_EXISTING_DECK_ID, new CardDetail("hello world ?"));
    }

    @Test
    public void addACard_when_deckExists() {
        when(this.deckRepository.findDeckById(DECK_ID)).thenReturn(Optional.of(new Deck(DECK_ID, "The deck")));
        this.deckService.addCard(DECK_ID, new CardDetail("hello world ?"));
        verify(this.deckRepository, times(1)).save(any());
    }
}
