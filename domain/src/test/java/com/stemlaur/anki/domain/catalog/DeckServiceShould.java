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
    private static final String DECK_ID = "4a1a2498-2f02-45b4-9570-58724b77a4e3";
    private static final String NON_EXISTING_DECK_ID = "ANYID";
    private static final String DECK_TITLE = "Neuro-fun";
    private static final String ANOTHER_DECK_TITLE = "Test deck";
    private static final String A_QUESTION = "What is the name of the standard used to order the human brain anatomical regions ?";
    private static final String A_ANSWER = "The neuroanatomy hierarchies.";

    private DeckService deckService;
    @Mock
    private DeckRepository deckRepository;

    @Before
    public void setUp() {
        this.deckService = new DeckService(this.deckRepository);
    }

    @Test
    public void createAnEmptyDeck() {
        final String id = this.deckService.create(DECK_TITLE);
        assertNotNull(id);
        verify(this.deckRepository, times(1)).save(any(Deck.class));
    }

    @Test
    public void createDecksWithDifferentId() {
        final String firstDeckId = this.deckService.create(DECK_TITLE);
        final String secondDeckId = this.deckService.create(ANOTHER_DECK_TITLE);
        assertNotEquals(firstDeckId, secondDeckId);
    }

    @Test
    public void removeADeck_when_itExists() {
        when(this.deckRepository.findDeckById(DECK_ID)).thenReturn(Optional.of(new Deck(DECK_ID, DECK_TITLE)));
        this.deckService.remove(DECK_ID);
        verify(this.deckRepository, times(1)).delete(DECK_ID);
    }

    @Test(expected = DeckService.DeckDoesNotExist.class)
    public void throwAnException_when_deckDoesNotExist() {
        when(deckRepository.findDeckById(NON_EXISTING_DECK_ID)).thenReturn(empty());
        this.deckService.remove(NON_EXISTING_DECK_ID);
    }

    @Test(expected = DeckService.DeckDoesNotExist.class)
    public void throwAnException_when_addingCardToANonExistingDeck() {
        when(deckRepository.findDeckById(NON_EXISTING_DECK_ID)).thenReturn(empty());
        this.deckService.addCard(NON_EXISTING_DECK_ID, new CardDetail(A_QUESTION, A_ANSWER));
    }

    @Test
    public void addACard_when_deckExists() {
        when(this.deckRepository.findDeckById(DECK_ID)).thenReturn(Optional.of(new Deck(DECK_ID, DECK_TITLE)));
        this.deckService.addCard(DECK_ID, new CardDetail(A_QUESTION, A_ANSWER));
        verify(this.deckRepository, times(1)).save(any(Deck.class));
    }
}
