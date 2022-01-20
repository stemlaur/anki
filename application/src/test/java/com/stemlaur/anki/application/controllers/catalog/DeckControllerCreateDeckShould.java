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

import com.stemlaur.anki.domain.catalog.api.CreateDeck;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeckControllerCreateDeckShould {
    private static final String DECK_ID = "2132a2a8-ca3f-4b3a-bc6f-9f1248944f2d";
    private static final String DECK_TITLE = "ANY TITLE";
    private DeckController deckController;

    @Mock
    private CreateDeck createDeck;

    @BeforeEach
    public void setUp() {
        this.deckController = new DeckController(createDeck, null, null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void createDeck() {
        when(this.createDeck.create(DECK_TITLE)).thenReturn(DECK_ID);
        ResponseEntity<String> responseEntity = deckController.createDeck(new CreateDeckRequest(DECK_TITLE));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/" + DECK_ID);
        assertThat(responseEntity.getBody()).isEqualTo(DECK_ID);
    }

    @Test
    public void return500Code_when_ExceptionOccured() {
        when(this.createDeck.create(DECK_TITLE)).thenThrow(new RuntimeException());
        ResponseEntity<String> responseEntity = deckController.createDeck(new CreateDeckRequest(DECK_TITLE));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
