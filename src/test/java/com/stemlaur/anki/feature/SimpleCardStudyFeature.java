package com.stemlaur.anki.feature;

import com.stemlaur.anki.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCardStudyFeature {

    private DeckService deckService;
    private UserService userService;
    private DeckStudyService deckStudyService;
    private SessionIdFactory sessionIdFactory;

    @Before
    public void setUp() throws Exception {
        this.deckService = new DeckService();
        this.sessionIdFactory = new SessionIdFactory();
        this.deckStudyService = new DeckStudyService(this.deckService, this.sessionIdFactory);
        this.userService = new UserService();
    }

    @Test
    public void studentCanStudyACardFromADeck() {
        final String contributorId = this.userService.addContributor();
        final String studentId = this.userService.addStudent();
        final String deckId = this.deckService.create(contributorId, "My first Deck !");
        this.deckService.addCard(contributorId, deckId, new CardDetail("Who is Uncle Bob ?"));

        final String sessionId = this.deckStudyService.startStudySession(studentId, deckId);
        final Optional<CardToStudy> firstStudyCard = this.deckStudyService.nextCardToStudy(studentId, sessionId);

        assertEquals("Who is Uncle Bob ?", firstStudyCard.get().question());
    }
}
