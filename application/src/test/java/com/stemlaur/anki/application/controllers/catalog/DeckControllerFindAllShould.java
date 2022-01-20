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

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckId;
import com.stemlaur.anki.domain.catalog.DeckTitle;
import com.stemlaur.anki.domain.catalog.api.FindDecks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeckControllerFindAllShould {
    private static final DeckId DECK_ID = DeckId.from("any id");
    private static final String DECK_TITLE = "ANY TITLE";
    private DeckController deckController;

    @Mock
    private FindDecks findDecks;

    @BeforeEach
    public void setUp() {
        this.deckController = new DeckController(null, null, findDecks);
    }

    @Test
    public void returnNoDecks_when_noneExists() {
        when(findDecks.all()).thenReturn(new ArrayList<>());
        final List<DeckDTO> actualDecks = this.deckController.findAll().getBody();
        assertTrue(notNull(actualDecks).isEmpty());
    }

    @Test
    public void returnDecks_when_exists() {
        when(findDecks.all()).thenReturn(Collections.singleton(new Deck(DECK_ID, new DeckTitle(DECK_TITLE))));
        final List<DeckDTO> actualDecks = this.deckController.findAll().getBody();
        assertEquals(1, notNull(actualDecks).size());
        assertEquals(new DeckDTO(DECK_ID.toString(), DECK_TITLE), actualDecks.get(0));
    }

    @Test
    public void return500Code_when_ExceptionOccured() {
        when(findDecks.all()).thenThrow(new RuntimeException());
        final ResponseEntity<List<DeckDTO>> responseEntity = this.deckController.findAll();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
