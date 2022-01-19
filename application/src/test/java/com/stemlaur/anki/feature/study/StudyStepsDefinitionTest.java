/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.stemlaur.anki.feature.study;

import com.stemlaur.anki.application.Application;
import com.stemlaur.anki.feature.common.DeckHttpClient;
import com.stemlaur.anki.feature.common.StudyHttpClient;
import com.stemlaur.anki.feature.common.StudyHttpClient.CardToStudy;
import com.stemlaur.anki.feature.common.TestConfiguration;
import com.stemlaur.anki.feature.common.World;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(classes = {Application.class, TestConfiguration.class})
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT, printOnlyOnFailure = false)
public class StudyStepsDefinitionTest {

    @Autowired
    DeckHttpClient deckHttpClient;
    @Autowired
    StudyHttpClient studyHttpClient;

    @Autowired
    World world;

    @Given("I create a deck with title {string}")
    public void i_create_a_deck_with_title(String deckTitle) {
        world.setDeckId(deckHttpClient.create(deckTitle));
    }

    @Given("I add a card with question {string} and answer {string} to the deck")
    public void i_add_a_card_with_question_and_answer_to_the_deck(String question, String answer) {
        deckHttpClient.addCard(world.getDeckId(), question, answer);
    }

    @Given("I start a study session for the deck")
    public void i_start_a_study_session_for_the_deck() {
        world.setSessionId(studyHttpClient.create(world.getDeckId()));
    }

    @When("I ask for the next card to study")
    public void i_ask_for_the_next_card_to_study() {
        world.pushCardToStudy(studyHttpClient.nextCard(world.getSessionId()));
    }

    @Then("The card should have question {string} and answer {string}")
    public void the_card_should_have_question_and_answer(String question, String answer) {
        final CardToStudy actual = world.popCardToStudy();

        assertNotNull(actual.getId());
        assertEquals(question, actual.getQuestion());
        assertEquals(answer, actual.getAnswer());
    }

    @Given("I study the card with opinion {string}")
    public void i_study_the_card_with_opinion(final String opinion) {
        final CardToStudy cardToStudy = this.world.peekCardToStudy();
        this.studyHttpClient.study(this.world.getSessionId(), cardToStudy.getId(), opinion);
    }

    @Then("the second card should be different")
    public void the_second_card_should_be_different() {
        final List<CardToStudy> allCardsToStudy = this.world.getListOfCardsToStudy();
        final CardToStudy firstCard = allCardsToStudy.get(0);
        final CardToStudy secondCard = allCardsToStudy.get(1);
        assertNotEquals(firstCard.getId(), secondCard.getId());
        assertNotEquals(firstCard.getQuestion(), secondCard.getQuestion());
    }
}
