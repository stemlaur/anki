package com.stemlaur.anki.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static java.util.Optional.*;
import static java.util.Optional.empty;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeckStudyServiceStartStudySessionShould {

    private static final String DECK_ID = "1234";
    private static final String STUDENT_ID = "54321";
    private static final String SESSION_ID = "901240234";
    private static final String TECH_QUESTION = "??";

    private DeckStudyService deckStudyService;
    @Mock
    private DeckService deckService;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private SessionIdFactory sessionIdFactory;

    @Before
    public void setUp() throws Exception {
        this.deckStudyService = new DeckStudyService(
                this.deckService, this.sessionIdFactory, this.sessionRepository);
        when(this.sessionIdFactory.create()).thenReturn(SESSION_ID);
    }

    @Test(expected = DeckStudyService.DeckDoesNotExist.class)
    public void throwAnException_when_theDeckDoesNotExist() {
        when(this.deckService.findDeckById(DECK_ID)).thenReturn(empty());
        this.deckStudyService.startStudySession(STUDENT_ID, DECK_ID);
    }

    @Test(expected = DeckStudyService.DeckDoesNotContainAnyCards.class)
    public void throwAnException_when_deckDoesNotContainAnyCard() {
        when(this.deckService.findDeckById(DECK_ID)).thenReturn(of(aDeck(DECK_ID)));
        this.deckStudyService.startStudySession(STUDENT_ID, DECK_ID);
    }

    @Test
    public void saveCardProgress() {
        when(this.deckService.findDeckById(DECK_ID))
                .thenReturn(of(aDeck(DECK_ID, new CardDetail(TECH_QUESTION))));

        this.deckStudyService.startStudySession(STUDENT_ID, DECK_ID);

        verify(this.sessionRepository, times(1))
                .save(new Session(
                        SESSION_ID, STUDENT_ID,
                        Collections.singleton(new CardToStudy(any(), TECH_QUESTION))));
    }

    private Deck aDeck(final String deckId, final CardDetail... cardDetails) {
        final Deck deck = new Deck(deckId, "My first deck");
        for (CardDetail cardDetail : cardDetails) {
            deck.addCard(cardDetail);
        }
        return deck;
    }
}