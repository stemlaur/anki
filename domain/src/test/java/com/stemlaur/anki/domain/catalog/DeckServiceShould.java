package com.stemlaur.anki.domain.catalog;

import com.stemlaur.anki.domain.catalog.spi.Decks;
import com.stemlaur.anki.domain.common.spi.fake.FakeDomainEvents;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public final class DeckServiceShould {
    private static final DeckId DECK_ID = DeckId.from("any id");
    private static final String NON_EXISTING_DECK_ID = "ANYID";
    private static final String DECK_TITLE = "Neuro-fun";
    private static final String ANOTHER_DECK_TITLE = "Test deck";
    private static final String A_QUESTION = "What is the name of the standard used to order the human brain anatomical regions ?";
    private static final String A_ANSWER = "The neuroanatomy hierarchies.";

    private final FakeDomainEvents fakeDomainEvents = new FakeDomainEvents();
    private DumbDeckIdFactory fakeDeckIdFactory;
    private DeckService deckService;
    @Mock
    private Decks decks;

    @BeforeEach
    public void setUp() {
        this.fakeDeckIdFactory = new DumbDeckIdFactory("a_dumb_id");

        this.deckService = new DeckService(this.decks,
                this.fakeDeckIdFactory,
                this.fakeDomainEvents);
    }

    @AfterEach
    public void tearDown() {
        this.fakeDomainEvents.clear();
    }

    @Test
    public void createAnEmptyDeck() {
        final String id = this.deckService.create(DECK_TITLE);
        assertNotNull(id);
        verify(this.decks, times(1)).save(any(Deck.class));
    }

    @Test
    public void createDecksWithDifferentId() {
        final String firstDeckId = this.deckService.create(DECK_TITLE);

        fakeDeckIdFactory.changedFixedId("another id");

        final String secondDeckId = this.deckService.create(ANOTHER_DECK_TITLE);
        assertNotEquals(firstDeckId, secondDeckId);
    }

    @Test
    public void publishDeckCreated() {
        this.deckService.create(DECK_TITLE);

        assertThat(fakeDomainEvents.getEvents())
                .containsExactly(new DeckCreated(DeckId.from("a_dumb_id"), DECK_TITLE));
    }

    @Test
    public void removeADeck_when_itExists() {
        when(this.decks.find(DECK_ID.getValue())).thenReturn(Optional.of(new Deck(DECK_ID, new DeckTitle(DECK_TITLE))));
        this.deckService.remove(DECK_ID.getValue());
        verify(this.decks, times(1)).delete(DECK_ID.getValue());
    }

    @Test
    public void throwAnException_when_deckDoesNotExist() {
        when(decks.find(NON_EXISTING_DECK_ID)).thenReturn(empty());

        assertThrows(DeckDoesNotExist.class,
                () -> this.deckService.remove(NON_EXISTING_DECK_ID));
    }

    @Test
    public void throwAnException_when_addingCardToANonExistingDeck() {
        when(decks.find(NON_EXISTING_DECK_ID)).thenReturn(empty());

        assertThrows(DeckDoesNotExist.class,
                () -> this.deckService.addCard(NON_EXISTING_DECK_ID, new CardDetail(A_QUESTION, A_ANSWER)));
    }

    @Test
    public void addACard_when_deckExists() {
        when(this.decks.find(DECK_ID.getValue())).thenReturn(Optional.of(new Deck(DECK_ID, new DeckTitle(DECK_TITLE))));
        this.deckService.addCard(DECK_ID.getValue(), new CardDetail(A_QUESTION, A_ANSWER));
        verify(this.decks, times(1)).save(any(Deck.class));
    }

    @Test
    public void findAllDecks() {
        this.deckService.all();
        verify(this.decks, times(1)).findAll();
    }
}
