package com.stemlaur.anki.rest.controllers.catalog;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeckControllerFindAllShould {
    private static final String DECK_ID = "2132a2a8-ca3f-4b3a-bc6f-9f1248944f2d";
    private static final String DECK_TITLE = "ANY TITLE";
    private DeckController deckController;

    @Mock
    private DeckService deckService;

    @Before
    public void setUp() {
        this.deckController = new DeckController(deckService);
    }

    @Test
    public void returnNoDecks_when_noneExists() {
        when(deckService.findAll()).thenReturn(new ArrayList<>());
        final List<DeckDTO> actualDecks = this.deckController.findAll().getBody();
        assertTrue(actualDecks.isEmpty());
    }

    @Test
    public void returnDecks_when_exists() {
        when(deckService.findAll()).thenReturn(Collections.singleton(new Deck(DECK_ID, DECK_TITLE)));
        final List<DeckDTO> actualDecks = this.deckController.findAll().getBody();
        assertEquals(1, actualDecks.size());
        assertEquals(new DeckDTO(DECK_ID, DECK_TITLE), actualDecks.get(0));
    }

    @Test
    public void return500Code_when_ExceptionOccured() {
        when(deckService.findAll()).thenThrow(new RuntimeException());
        final ResponseEntity<List<DeckDTO>> responseEntity = this.deckController.findAll();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
