package com.stemlaur.anki.feature;

import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.common.Clock;
import com.stemlaur.anki.domain.study.*;
import com.stemlaur.anki.infrastructure.InMemorySessionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCardStudyFeature {
    private static final String DECK_TITLE = "Brain and its mysteries";
    private static final String A_QUESTION = "What part of the brain is primarily involved in visual perception ?";
    private static final String AN_ANSWER = "Occipital lobe";

    private DeckStudyService deckStudyService;
    private CardProgressService cardProgressService;
    private String sessionId;

    @Before
    public void setUp() {
        final DeckService deckService = new DeckService();
        this.cardProgressService = new CardProgressService();
        this.deckStudyService = new DeckStudyService(deckService, this.cardProgressService, new SessionIdFactory(), new InMemorySessionRepository(), new Clock());

        final String deckId = deckService.create(DECK_TITLE);
        deckService.addCard(deckId, new CardDetail(A_QUESTION, AN_ANSWER));
        this.sessionId = this.deckStudyService.startStudySession(deckId);
    }

    @Test
    public void userCanStudyACardFromADeck() {
        final Optional<CardToStudy> firstStudyCard = this.deckStudyService.nextCardToStudy(sessionId);
        final CardToStudy actual = firstStudyCard.orElseThrow();
        assertNotNull(actual.id());
        assertEquals(A_QUESTION, actual.question());
        assertEquals(AN_ANSWER, actual.answer());
    }

    @Test
    public void studyingACardWithAGreenOpinionChangesItsProgress() {
        final CardToStudy cartToStudy = this.deckStudyService.nextCardToStudy(sessionId).orElseThrow();

        this.deckStudyService.study(sessionId, cartToStudy.id(), Opinion.RED);
        this.deckStudyService.study(sessionId, cartToStudy.id(), Opinion.ORANGE);
        this.deckStudyService.study(sessionId, cartToStudy.id(), Opinion.GREEN);

        CardProgress actual = this.cardProgressService.findByCardToStudyId(cartToStudy.id());
        assertEquals(Duration.of(10, SECONDS), actual.durationBeforeNextEvaluation());
    }
}
