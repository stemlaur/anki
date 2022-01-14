package com.stemlaur.anki.application.controllers.catalog;

import com.stemlaur.anki.application.controllers.catalog.AddCardRequest;
import com.stemlaur.anki.application.controllers.catalog.DeckController;
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
                deckController.addCard(DECK_ID, new AddCardRequest("question", "answer"));
        verify(this.addCard, times(1)).addCard(DECK_ID, new CardDetail("question", "answer"));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void return404Code_when_DeckDoesNotExists() {
        doThrow(new DeckDoesNotExist(DECK_ID)).when(this.addCard)
                .addCard(DECK_ID, new CardDetail("question", "answer"));
        ResponseEntity<?> responseEntity =
                deckController.addCard(DECK_ID, new AddCardRequest("question", "answer"));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void return500Code_when_ExceptionOccurred() {
        doThrow(new RuntimeException()).when(this.addCard)
                .addCard(DECK_ID, new CardDetail("question", "answer"));
        ResponseEntity<?> responseEntity =
                deckController.addCard(DECK_ID, new AddCardRequest("question", "answer"));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
