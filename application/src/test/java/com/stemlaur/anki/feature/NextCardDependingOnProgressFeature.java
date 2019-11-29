package com.stemlaur.anki.feature;

import com.stemlaur.anki.infrastructure.InMemoryCardProgressRepository;
import com.stemlaur.anki.infrastructure.InMemoryDeckRepository;
import com.stemlaur.anki.infrastructure.InMemorySessionRepository;
import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.common.Clock;
import com.stemlaur.anki.domain.study.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class NextCardDependingOnProgressFeature {
    private static final String DECK_TITLE = "Brain and its mysteries";
    private static final String A_QUESTION = "What part of the brain is primarily involved in visual perception ?";
    private static final String AN_ANSWER = "Occipital lobe";
    private static final String ANOTHER_QUESTION = "What is the lunate sulcus ?";
    private static final String ANOTHER_ANSWER = "A fissure in the occipital lobe.";

    private DeckStudyService deckStudyService;
    private String sessionId;

    @Before
    public void setUp() {
        final DeckService deckService = new DeckService(new InMemoryDeckRepository());
        final CardProgressService cardProgressService = new CardProgressService(new InMemoryCardProgressRepository());
        this.deckStudyService = new DeckStudyService(deckService, cardProgressService, new SessionIdFactory(), new InMemorySessionRepository(), new Clock());

        final String deckId = deckService.create(DECK_TITLE);
        deckService.addCard(deckId, new CardDetail(A_QUESTION, AN_ANSWER));
        deckService.addCard(deckId, new CardDetail(ANOTHER_QUESTION, ANOTHER_ANSWER));
        this.sessionId = this.deckStudyService.startStudySession(deckId);
    }

    @Test
    public void nextCardShouldDependOnItsScore() {
        final CardToStudy firstCard = this.deckStudyService.nextCardToStudy(sessionId).orElseThrow();
        this.deckStudyService.study(sessionId, firstCard.id(), Opinion.GREEN);
        final CardToStudy secondCard = this.deckStudyService.nextCardToStudy(sessionId).orElseThrow();

        assertNotEquals(firstCard.id(), secondCard.id());
        assertNotEquals(firstCard.question(), secondCard.question());
    }
}
