package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeckStudyServiceStartStudySessionShould {

    private static final String DECK_ID = "1234";
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

    @Before
    public void setUp() {
        this.deckStudyService = new DeckStudyService(
                this.deckService, null, this.sessionIdFactory, this.sessions, null);
        when(this.sessionIdFactory.create()).thenReturn(SESSION_ID);
    }

    @Test(expected = DeckStudyService.DeckDoesNotExist.class)
    public void throwAnException_when_theDeckDoesNotExist() {
        when(this.deckService.findDeckById(DECK_ID)).thenReturn(empty());
        this.deckStudyService.startStudySession(DECK_ID);
    }

    @Test(expected = DeckStudyService.DeckDoesNotContainAnyCards.class)
    public void throwAnException_when_deckDoesNotContainAnyCard() {
        when(this.deckService.findDeckById(DECK_ID)).thenReturn(of(aDeck()));
        this.deckStudyService.startStudySession(DECK_ID);
    }

    @Test
    public void saveCardProgress() {
        when(this.deckService.findDeckById(DECK_ID))
                .thenReturn(of(aDeck(new CardDetail(A_QUESTION, AN_ANSWER))));

        this.deckStudyService.startStudySession(DECK_ID);

        verify(this.sessions, times(1))
                .save(new Session(
                        SESSION_ID,
                        Collections.singleton(new CardToStudy(any(), A_QUESTION, AN_ANSWER))));
    }

    private Deck aDeck(final CardDetail... cardDetails) {
        final Deck deck = new Deck(DeckStudyServiceStartStudySessionShould.DECK_ID, DECK_TITLE);
        for (CardDetail cardDetail : cardDetails) {
            deck.addCard(cardDetail);
        }
        return deck;
    }
}