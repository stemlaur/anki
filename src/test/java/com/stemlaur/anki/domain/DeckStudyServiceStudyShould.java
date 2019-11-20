package com.stemlaur.anki.domain;

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
public class DeckStudyServiceStudyShould {
    private static final String SESSION_ID = "1349234";
    private static final String SOME_CARD_TO_STUDY_ID = "24942394";
    private static final String CARD_TO_STUDY_ID = "92400234";
    private static final String QUESTION = "Who are you ?";

    private DeckStudyService deckStudyService;
    @Mock
    private DeckService deckService;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private CardProgressService cardProgressService;
    @Mock
    private Clock clock;

    @Before
    public void setUp() {
        this.deckStudyService = new DeckStudyService(
                this.deckService,
                this.cardProgressService,
                null,
                this.sessionRepository,
                this.clock);
    }

    @Test(expected = DeckStudyService.SessionDoesNotExist.class)
    public void throwAnException_when_SessionDoesNotExist() {
        when(this.sessionRepository.findById(SESSION_ID)).thenReturn(empty());
        this.deckStudyService.study(SESSION_ID, SOME_CARD_TO_STUDY_ID, Opinion.GREEN);
    }

    @Test(expected = DeckStudyService.CardDoesNotExistInTheSession.class)
    public void throwAnException_when_CardDoesNotExistInTheSession() {
        when(this.sessionRepository.findById(SESSION_ID)).thenReturn(
                of(new Session(SESSION_ID, Collections.singleton(new CardToStudy(CARD_TO_STUDY_ID, QUESTION)))));
        this.deckStudyService.study(SESSION_ID, SOME_CARD_TO_STUDY_ID, Opinion.GREEN);
    }

    @Test
    public void saveCardProgress_when_doesNotAlreadyExist() {
        when(this.sessionRepository.findById(SESSION_ID)).thenReturn(
                of(new Session(SESSION_ID, Collections.singleton(new CardToStudy(CARD_TO_STUDY_ID, QUESTION)))));
        this.deckStudyService.study(SESSION_ID, CARD_TO_STUDY_ID, Opinion.GREEN);
        verify(this.cardProgressService, times(1)).save(any(CardProgress.class));
    }

    @Test
    public void saveCardProgress_when_alreadyExist() {
        when(this.sessionRepository.findById(SESSION_ID)).thenReturn(
                of(new Session(SESSION_ID, Collections.singleton(new CardToStudy(CARD_TO_STUDY_ID, QUESTION)))));
        this.deckStudyService.study(SESSION_ID, CARD_TO_STUDY_ID, Opinion.GREEN);
        this.deckStudyService.study(SESSION_ID, CARD_TO_STUDY_ID, Opinion.GREEN);
        verify(this.cardProgressService, times(2)).save(any(CardProgress.class));
    }
}