package com.stemlaur.anki.feature;

import com.stemlaur.anki.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCardStudyFeature {

    private DeckService deckService;
    private DeckStudyService deckStudyService;

    @Before
    public void setUp() {
        this.deckService = new DeckService();
        this.deckStudyService = new DeckStudyService(this.deckService, new SessionIdFactory());
    }

    @Test
    public void userCanStudyACardFromADeck() {
        final String deckId = this.deckService.create("My first Deck !");
        this.deckService.addCard(deckId, new CardDetail("Who is Uncle Bob ?"));

        final String sessionId = this.deckStudyService.startStudySession(deckId);
        final Optional<CardToStudy> firstStudyCard = this.deckStudyService.nextCardToStudy(sessionId);

        final CardToStudy actual = firstStudyCard.orElseThrow();
        assertNotNull(actual.id());
        assertEquals("Who is Uncle Bob ?", actual.question());
    }
}
