package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.catalog.*;
import com.stemlaur.anki.domain.study.spi.Sessions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeckStudyServiceStartStudySessionShould {

    private static final DeckId DECK_ID = DeckId.from("any id");
    private static final String SESSION_ID = "901240234";
    private static final String DECK_TITLE = "The lobes we love.";
    private static final String A_QUESTION = "Which lobe is the largest of the four major lobes of the brain in mammals ?";
    private static final String AN_ANSWER = "The frontal lobe.";

    private DeckStudyService deckStudyService;
    @Mock
    private DeckService deckService;
    @Mock
    private Sessions sessions;
    @Mock
    private SessionIdFactory sessionIdFactory;

    @BeforeEach
    public void setUp() {
        this.deckStudyService = new DeckStudyService(
                this.deckService, null, this.sessionIdFactory, this.sessions, null);
        when(this.sessionIdFactory.create()).thenReturn(SESSION_ID);
    }

    @Test
    public void throwAnException_when_theDeckDoesNotExist() {
        when(this.deckService.byId(DECK_ID.getValue())).thenReturn(empty());

        assertThrows(DeckDoesNotExist.class,
                () -> this.deckStudyService.startStudySession(DECK_ID.getValue()));
    }

    @Test
    public void throwAnException_when_deckDoesNotContainAnyCard() {
        when(this.deckService.byId(DECK_ID.getValue())).thenReturn(of(aDeck()));

        assertThrows(DeckDoesNotContainAnyCards.class,
                () -> this.deckStudyService.startStudySession(DECK_ID.getValue()));
    }

    @Test
    public void saveCardProgress() {
        when(this.deckService.byId(DECK_ID.getValue()))
                .thenReturn(of(aDeck(new CardDetail(A_QUESTION, AN_ANSWER))));

        this.deckStudyService.startStudySession(DECK_ID.getValue());

        verify(this.sessions, times(1))
                .save(new Session(
                        SESSION_ID,
                        Collections.singleton(new CardToStudy(any(), A_QUESTION, AN_ANSWER))));
    }

    private Deck aDeck(final CardDetail... cardDetails) {
        final Deck deck = new Deck(DECK_ID, new DeckTitle(DECK_TITLE));
        for (CardDetail cardDetail : cardDetails) {
            deck.addCard(cardDetail);
        }
        return deck;
    }
}
