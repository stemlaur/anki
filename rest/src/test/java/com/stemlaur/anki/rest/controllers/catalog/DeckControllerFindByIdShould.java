package com.stemlaur.anki.rest.controllers.catalog;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeckControllerFindByIdShould {
    private static final String DECK_ID = "2132a2a8-ca3f-4b3a-bc6f-9f1248944f2d";
    private static final String DECK_TITLE = "ANY TITLE";
    private DeckController deckController;

    @Mock
    private DeckService deckService;

    @BeforeEach
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

    @Test
    public void return500Code_when_ExceptionOccured() {
        when(deckService.findDeckById(DECK_ID)).thenThrow(new RuntimeException());
        ResponseEntity responseEntity = this.deckController.findDeckById(DECK_ID);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
