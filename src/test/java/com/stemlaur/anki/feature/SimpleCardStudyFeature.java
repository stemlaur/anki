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

    private DeckStudyService deckStudyService;
    private CardProgressService cardProgressService;
    private String sessionId;

    @Before
    public void setUp() {
        final DeckService deckService = new DeckService();
        this.cardProgressService = new CardProgressService();
        this.deckStudyService = new DeckStudyService(deckService, this.cardProgressService, new SessionIdFactory(), new InMemorySessionRepository(), new Clock());

        final String deckId = deckService.create("My first Deck !");
        deckService.addCard(deckId, new CardDetail("Who is Uncle Bob ?", "The answer"));
        this.sessionId = this.deckStudyService.startStudySession(deckId);
    }

    @Test
    public void userCanStudyACardFromADeck() {
        final Optional<CardToStudy> firstStudyCard = this.deckStudyService.nextCardToStudy(sessionId);
        final CardToStudy actual = firstStudyCard.orElseThrow();
        assertNotNull(actual.id());
        assertEquals("Who is Uncle Bob ?", actual.question());
    }

    @Test
    public void studyingACardWithAGreenOpinionChangesItsProgress() {
        final CardToStudy cartToStudy = this.deckStudyService.nextCardToStudy(sessionId).orElseThrow();

        this.deckStudyService.study(sessionId, cartToStudy.id(), Opinion.RED);
        this.deckStudyService.study(sessionId, cartToStudy.id(), Opinion.ORANGE);
        this.deckStudyService.study(sessionId, cartToStudy.id(), Opinion.GREEN);

        CardProgress actual = this.cardProgressService.findByCardToStudyId(cartToStudy.id()).orElseThrow();
        assertEquals(Duration.of(10, SECONDS), actual.durationBeforeNextEvaluation());
    }
}
