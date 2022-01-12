package com.stemlaur.anki.controllers.catalog;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckId;
import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.catalog.DeckTitle;
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
    private static final DeckId DECK_ID = DeckId.of();
    private static final String DECK_TITLE = "ANY TITLE";
    private DeckController deckController;

    @Mock
    private DeckService deckService;

    @BeforeEach
    public void setUp() {
        this.deckController = new DeckController(deckService);
    }

    @Test
    public void returnNoDecks_when_noneExists() {
        when(deckService.all()).thenReturn(new ArrayList<>());
        final List<DeckDTO> actualDecks = this.deckController.findAll().getBody();
        assertTrue(notNull(actualDecks).isEmpty());
    }

    @Test
    public void returnDecks_when_exists() {
        when(deckService.all()).thenReturn(Collections.singleton(new Deck(DECK_ID, new DeckTitle(DECK_TITLE))));
        final List<DeckDTO> actualDecks = this.deckController.findAll().getBody();
        assertEquals(1, notNull(actualDecks).size());
        assertEquals(new DeckDTO(DECK_ID.toString(), DECK_TITLE), actualDecks.get(0));
    }

    @Test
    public void return500Code_when_ExceptionOccured() {
        when(deckService.all()).thenThrow(new RuntimeException());
        final ResponseEntity<List<DeckDTO>> responseEntity = this.deckController.findAll();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
