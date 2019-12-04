package com.stemlaur.anki.feature;

import com.stemlaur.anki.application.Application;
import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.common.Clock;
import com.stemlaur.anki.domain.study.CardProgressService;
import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.SessionIdFactory;
import com.stemlaur.anki.feature.steps.IAddACardToADeck;
import com.stemlaur.anki.feature.steps.IAddACardToADeck.Card;
import com.stemlaur.anki.feature.steps.IAskForNextCardToStudy;
import com.stemlaur.anki.feature.steps.ICreateADeck;
import com.stemlaur.anki.feature.steps.IStartAStudySession;
import com.stemlaur.anki.infrastructure.InMemoryCardProgressRepository;
import com.stemlaur.anki.infrastructure.InMemoryDeckRepository;
import com.stemlaur.anki.infrastructure.InMemorySessionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.stemlaur.anki.feature.steps.BDD.and;
import static com.stemlaur.anki.feature.steps.BDD.given;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT, printOnlyOnFailure = false)
public class SimpleCardStudyITFeature {
    private static final String DECK_TITLE = "Brain and its mysteries";
    private static final String A_QUESTION = "What part of the brain is primarily involved in visual perception ?";
    private static final String AN_ANSWER = "Occipital lobe";

    @Autowired
    private MockMvc mockMvc;
    private String sessionId;

    @Before
    public void setUp() throws Exception {
        final ICreateADeck iCreateADeck = new ICreateADeck(this.mockMvc);
        final IAddACardToADeck iAddACardToADeck = new IAddACardToADeck(this.mockMvc);
        final IStartAStudySession iStartAStudySession = new IStartAStudySession(this.mockMvc);

        final String deckId = given(iCreateADeck).with(DECK_TITLE);
        and(iAddACardToADeck).with(deckId, new Card(A_QUESTION, AN_ANSWER));
        this.sessionId = and(iStartAStudySession).with(deckId);
    }

    @Test
    public void userCanStudyACardFromADeck() throws Exception {
        IAskForNextCardToStudy iAskForNextCardToStudy = new IAskForNextCardToStudy(this.mockMvc);
        final IAskForNextCardToStudy.CardToStudy actual = and(iAskForNextCardToStudy).with(this.sessionId);
        assertNotNull(actual.getId());
        Assert.assertEquals(A_QUESTION, actual.getQuestion());
        Assert.assertEquals(AN_ANSWER, actual.getAnswer());
    }
}
