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
package com.stemlaur.anki.application.controllers.catalog;

import com.stemlaur.anki.application.controllers.model.AddCardRequest;
import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.DeckDoesNotExist;
import com.stemlaur.anki.domain.catalog.api.AddCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeckControllerAddCardShould {
    private static final String DECK_ID = "2132a2a8-ca3f-4b3a-bc6f-9f1248944f2d";

    private DeckController deckController;

    @Mock
    private AddCard addCard;

    @BeforeEach
    public void setUp() {
        this.deckController = new DeckController(null, addCard, null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void return200Code_when_DeckExists() {
        ResponseEntity<?> responseEntity =
                deckController.addCardToDeck(DECK_ID, new AddCardRequest().question("question").answer("answer"));
        verify(this.addCard, times(1)).addCard(DECK_ID, new CardDetail("question", "answer"));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void return404Code_when_DeckDoesNotExists() {
        doThrow(new DeckDoesNotExist(DECK_ID)).when(this.addCard)
                .addCard(DECK_ID, new CardDetail("question", "answer"));
        ResponseEntity<?> responseEntity =
                deckController.addCardToDeck(DECK_ID, new AddCardRequest().question("question").answer("answer"));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void return500Code_when_ExceptionOccurred() {
        doThrow(new RuntimeException()).when(this.addCard)
                .addCard(DECK_ID, new CardDetail("question", "answer"));
        ResponseEntity<?> responseEntity =
                deckController.addCardToDeck(DECK_ID, new AddCardRequest().question("question").answer("answer"));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
