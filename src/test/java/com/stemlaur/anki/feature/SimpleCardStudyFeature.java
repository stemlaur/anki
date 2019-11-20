package com.stemlaur.anki.feature;

import com.stemlaur.anki.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCardStudyFeature {

    private DeckService deckService;
    private DeckStudyService deckStudyService;
    private CardProgressService cardProgressService;
    private String deckId;
    private String sessionId;
    private Clock clock;
    private LocalDateTime now;

    @Before
    public void setUp() {
        this.clock = new Clock();
        this.deckService = new DeckService();
        this.cardProgressService = new CardProgressService();
        this.deckStudyService = new DeckStudyService(this.deckService, this.cardProgressService, new Clock());
        this.deckId = this.deckService.create("My first Deck !");
        this.deckService.addCard(deckId, new CardDetail("Who is Uncle Bob ?"));
        this.sessionId = this.deckStudyService.startStudySession(deckId);
        this.now = clock.now();
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

        this.deckStudyService.study(sessionId, cartToStudy.id(), Opinion.GREEN);

        CardProgress actual = this.cardProgressService.findByCardToStudyId(cartToStudy.id()).orElseThrow();
        assertTrue(now.isBefore(actual.lastEvaluationAt()));
    }
}
