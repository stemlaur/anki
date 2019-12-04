package com.stemlaur.anki.feature;

import com.stemlaur.anki.application.Application;
import com.stemlaur.anki.feature.steps.*;
import com.stemlaur.anki.feature.steps.IAddACardToADeck.Card;
import com.stemlaur.anki.feature.steps.IAskForNextCardToStudy.CardToStudy;
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

import static com.stemlaur.anki.feature.steps.BDD.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT, printOnlyOnFailure = false)
public class SimpleCardStudyFeature {
    private static final String DECK_TITLE = "Brain and its mysteries";
    private static final String A_QUESTION = "What part of the brain is primarily involved in visual perception ?";
    private static final String AN_ANSWER = "Occipital lobe";

    @Autowired
    private MockMvc mockMvc;
    private ICreateADeck iCreateADeck;
    private IAddACardToADeck iAddACardToADeck;
    private IStartAStudySession iStartAStudySession;
    private IAskForNextCardToStudy iAskForNextCardToStudy;

    @Before
    public void setUp() {
        iCreateADeck = new ICreateADeck(this.mockMvc);
        iAddACardToADeck = new IAddACardToADeck(this.mockMvc);
        iStartAStudySession = new IStartAStudySession(this.mockMvc);
        iAskForNextCardToStudy = new IAskForNextCardToStudy(this.mockMvc);
    }

    @Test
    public void userCanStudyACardFromADeck() throws Exception {
        final String deckId = given(iCreateADeck).with(DECK_TITLE);
        and(iAddACardToADeck).with(deckId, new Card(A_QUESTION, AN_ANSWER));
        final String sessionId = and(iStartAStudySession).with(deckId);

        final CardToStudy actual = when(iAskForNextCardToStudy).with(sessionId);
        assertNotNull(actual.getId());
        Assert.assertEquals(A_QUESTION, actual.getQuestion());
        Assert.assertEquals(AN_ANSWER, actual.getAnswer());
    }
}
