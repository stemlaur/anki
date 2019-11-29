package com.stemlaur.anki.rest.controllers;

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
import java.util.Optional;

import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeckControllerFindByIdShould {
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
    public void returnCode404_when_noneExists() {
        when(deckService.findDeckById(DECK_ID)).thenReturn(empty());
        final ResponseEntity<?> responseEntity = this.deckController.findDeckById(DECK_ID);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void returnCode200_when_deckExists() {
        when(deckService.findDeckById(DECK_ID)).thenReturn(of(new Deck(DECK_ID, DECK_TITLE)));
        final ResponseEntity<?> responseEntity = this.deckController.findDeckById(DECK_ID);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void returnDeckDTO_when_deckExists() {
        when(deckService.findDeckById(DECK_ID)).thenReturn(of(new Deck(DECK_ID, DECK_TITLE)));
        final DeckDTO actual = (DeckDTO) this.deckController.findDeckById(DECK_ID).getBody();
        assertThat(actual).isEqualTo(new DeckDTO(DECK_ID, DECK_TITLE));
    }
}
