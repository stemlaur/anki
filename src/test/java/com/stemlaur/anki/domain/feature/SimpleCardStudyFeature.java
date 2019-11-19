package com.stemlaur.anki.domain.feature;

import com.stemlaur.anki.domain.CardDetail;
import com.stemlaur.anki.domain.DeckService;
import com.stemlaur.anki.domain.Opinion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCardStudyFeature {

    private DeckService deckService;
    private UserService userService;
    private DeckStudyService deckStudyService;

    @Before
    public void setUp() throws Exception {
        this.deckService = new DeckService();
        this.deckStudyService = new DeckStudyService();
        this.userService = new UserService();
    }

    @Test
    public void studentCanStudyADeck() {
        final String contributorId = this.userService.addContributor();
        final String studentId = this.userService.addStudent();
        final String deckId = this.deckService.create(contributorId, "My first Deck !");
        this.deckService.addCard(contributorId, deckId, new CardDetail("Who is Uncle Bob ?"));

        final String sessionId = this.deckStudyService.startStudySession(studentId, deckId);
        final CardToStudy firstStudyCard = this.deckStudyService.nextCardToStudy(studentId, sessionId);
        this.deckStudyService.study(studentId, sessionId, firstStudyCard.id(), Opinion.RED);
        final CardToStudy secondStudyCard = this.deckStudyService.nextCardToStudy(studentId, sessionId);

        assertEquals(firstStudyCard.id(), secondStudyCard.id());
    }
}
