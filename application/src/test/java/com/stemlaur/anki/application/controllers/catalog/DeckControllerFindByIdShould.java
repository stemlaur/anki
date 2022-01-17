package com.stemlaur.anki.application.controllers.catalog;

import com.stemlaur.anki.application.controllers.catalog.DeckController;
import com.stemlaur.anki.application.controllers.catalog.DeckDTO;
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

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeckControllerFindByIdShould {
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
    public void returnCode404_when_noneExists() {
        when(findDecks.byId(DECK_ID.getValue())).thenReturn(empty());
        final ResponseEntity<?> responseEntity = this.deckController.findDeckById(DECK_ID.getValue());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void returnCode200_when_deckExists() {
        when(findDecks.byId(DECK_ID.getValue())).thenReturn(of(new Deck(DECK_ID, new DeckTitle(DECK_TITLE))));
        final ResponseEntity<?> responseEntity = this.deckController.findDeckById(DECK_ID.getValue());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void returnDeckDTO_when_deckExists() {
        when(findDecks.byId(DECK_ID.getValue())).thenReturn(of(new Deck(DECK_ID, new DeckTitle(DECK_TITLE))));
        final DeckDTO actual = (DeckDTO) this.deckController.findDeckById(DECK_ID.getValue()).getBody();
        assertThat(actual).isEqualTo(new DeckDTO(DECK_ID.getValue(), DECK_TITLE));
    }

    @Test
    public void return500Code_when_ExceptionOccurred() {
        when(findDecks.byId(DECK_ID.getValue())).thenThrow(new RuntimeException());
        ResponseEntity<?> responseEntity = this.deckController.findDeckById(DECK_ID.getValue());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
