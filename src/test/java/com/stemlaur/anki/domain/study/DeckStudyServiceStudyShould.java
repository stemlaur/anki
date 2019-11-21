package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.common.Clock;
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
    private static final String SESSION_ID = "d62c203f-3eeb-4b4a-b493-52e590ad340a";
    private static final String SOME_CARD_TO_STUDY_ID = "7d0424be-6a80-4e3c-9adc-d86eddd6815d";
    private static final String CARD_TO_STUDY_ID = "0fd60c6d-7239-42a4-a445-d7e37ccf975a";
    private static final String A_QUESTION = "Which lobe is positioned above the temporal lobe and behind the frontal lobe ?";
    private static final String AN_ANSWER = "The parietal lobe.";

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
                of(new Session(SESSION_ID, Collections.singleton(new CardToStudy(CARD_TO_STUDY_ID, A_QUESTION, AN_ANSWER)))));
        this.deckStudyService.study(SESSION_ID, SOME_CARD_TO_STUDY_ID, Opinion.GREEN);
    }

    @Test
    public void saveCardProgress_when_doesNotAlreadyExist() {
        when(this.sessionRepository.findById(SESSION_ID)).thenReturn(
                of(new Session(SESSION_ID, Collections.singleton(new CardToStudy(CARD_TO_STUDY_ID, A_QUESTION, AN_ANSWER)))));
        when(this.cardProgressService.findByCardToStudyId(CARD_TO_STUDY_ID)).thenReturn(CardProgress.init(CARD_TO_STUDY_ID));
        this.deckStudyService.study(SESSION_ID, CARD_TO_STUDY_ID, Opinion.GREEN);
        verify(this.cardProgressService, times(1)).save(any(CardProgress.class));
    }

    @Test
    public void saveCardProgress_when_alreadyExist() {
        when(this.sessionRepository.findById(SESSION_ID)).thenReturn(
                of(new Session(SESSION_ID, Collections.singleton(new CardToStudy(CARD_TO_STUDY_ID, A_QUESTION, AN_ANSWER)))));
        when(this.cardProgressService.findByCardToStudyId(CARD_TO_STUDY_ID)).thenReturn(CardProgress.init(CARD_TO_STUDY_ID));

        this.deckStudyService.study(SESSION_ID, CARD_TO_STUDY_ID, Opinion.GREEN);
        this.deckStudyService.study(SESSION_ID, CARD_TO_STUDY_ID, Opinion.GREEN);
        verify(this.cardProgressService, times(2)).save(any(CardProgress.class));
    }
}