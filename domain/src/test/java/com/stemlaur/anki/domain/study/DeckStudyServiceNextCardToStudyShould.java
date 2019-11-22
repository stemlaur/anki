package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.catalog.DeckService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeckStudyServiceNextCardToStudyShould {
    private static final String SESSION_ID = "8c250114-fab1-465d-936b-739bc57ff33d";
    private static final String A_CARD_ID = "f2098356-8fbb-47fb-bad7-30a14aa3ed0c";
    private static final String A_QUESTION = "What lobe is involved in processing sensory input ?";
    private static final String AN_ANSWER = "The temporal lobe.";
    private static final String ANOTHER_CARD_ID = "9c75b6c7-c49f-4e3b-babe-cc58400c01ce";
    private static final String ANOTHER_QUESTION = "What is the lunate sulcus ?";
    private static final String ANOTHER_ANSWER = "A fissure in the occipital lobe.";

    private DeckStudyService deckStudyService;
    @Mock
    private DeckService deckService;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private CardProgressService cardProgressService;

    @Before
    public void setUp() {
        this.deckStudyService = new DeckStudyService(
                this.deckService, cardProgressService, null, this.sessionRepository, null);
    }

    @Test(expected = DeckStudyService.SessionDoesNotExist.class)
    public void throwAnException_when_sessionDoesNotExist() {
        when(this.sessionRepository.findById(SESSION_ID)).thenReturn(empty());
        this.deckStudyService.nextCardToStudy(SESSION_ID);
    }

    @Test
    public void returnACardToStudy_when_sessionContainsOne() {
        final Session session = new Session(SESSION_ID,
                Collections.singleton(new CardToStudy(A_CARD_ID, A_QUESTION, AN_ANSWER)));
        when(this.sessionRepository.findById(SESSION_ID)).thenReturn(of(session));
        when(this.cardProgressService.findByCardToStudyId(A_CARD_ID))
                .thenReturn(new CardProgress(A_CARD_ID, LocalDateTime.now(), new Score(10)));

        final Optional<CardToStudy> cardToStudy = this.deckStudyService.nextCardToStudy(SESSION_ID);
        assertFalse(cardToStudy.isEmpty());
    }

    @Test
    public void showTheCardWithTheLowestScore() {
        final List<CardToStudy> cardToStudies = asList(
                new CardToStudy(A_CARD_ID, A_QUESTION, AN_ANSWER),
                new CardToStudy(ANOTHER_CARD_ID, ANOTHER_QUESTION, ANOTHER_ANSWER));

        final Session session = new Session(SESSION_ID, new HashSet<>(cardToStudies));
        when(this.sessionRepository.findById(SESSION_ID)).thenReturn(of(session));
        when(this.cardProgressService.findByCardToStudyId(A_CARD_ID))
                .thenReturn(new CardProgress(A_CARD_ID, LocalDateTime.now(), new Score(10)));
        when(this.cardProgressService.findByCardToStudyId(ANOTHER_CARD_ID))
                .thenReturn(new CardProgress(ANOTHER_CARD_ID, LocalDateTime.now(), new Score(2)));

        final CardToStudy cardToStudy = this.deckStudyService.nextCardToStudy(SESSION_ID).orElseThrow();

        assertEquals(ANOTHER_CARD_ID, cardToStudy.id());
    }
}