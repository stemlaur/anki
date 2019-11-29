package com.stemlaur.anki.rest.controllers;

import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.DeckService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeckControllerAddCardShould {
    private static final String DECK_ID = "2132a2a8-ca3f-4b3a-bc6f-9f1248944f2d";
    private static final String DECK_TITLE = "ANY TITLE";

    private DeckController deckController;

    @Mock
    private DeckService deckService;

    @Before
    public void setUp() {
        this.deckController = new DeckController(deckService);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void return200Code_when_DeckExists() {
        ResponseEntity<?> responseEntity =
                deckController.addCard(DECK_ID, new AddCardRequest("question", "answer"));
        verify(this.deckService, times(1)).addCard(DECK_ID, new CardDetail("question", "answer"));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void return404Code_when_DeckDoesNotExists() {
        doThrow(new DeckService.DeckDoesNotExist()).when(this.deckService)
                .addCard(DECK_ID, new CardDetail("question", "answer"));
        ResponseEntity<?> responseEntity =
                deckController.addCard(DECK_ID, new AddCardRequest("question", "answer"));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void return500Code_when_ExceptionOccured() {
        doThrow(new RuntimeException()).when(this.deckService)
                .addCard(DECK_ID, new CardDetail("question", "answer"));
        ResponseEntity<?> responseEntity =
                deckController.addCard(DECK_ID, new AddCardRequest("question", "answer"));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
