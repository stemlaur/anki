package com.stemlaur.anki.rest.controllers.study;

import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.Opinion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StudyControllerStudyCardShould {
    private static final String SESSION_ID = "9b9dc6dd-18d3-45e4-92c1-399253ec954d";
    private static final String CARD_ID = "8e3760ec-013a-42b3-aa20-5b5f1506ca58";
    private static final StudyCardRequest REQUEST = new StudyCardRequest(CARD_ID, Opinion.GREEN);

    private StudyController studyController;
    @Mock
    private DeckStudyService deckStudyService;

    @BeforeEach
    public void setUp() {
        this.studyController = new StudyController(deckStudyService);
    }

    @Test
    public void return200_when_isPresent() {
        ResponseEntity<?> responseEntity = studyController.studyCard(SESSION_ID, REQUEST);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        verify(this.deckStudyService, times(1)).study(SESSION_ID, CARD_ID, Opinion.GREEN);
    }

    @Test
    public void return404_when_sessionDoesNotExist() {
        doThrow(new DeckStudyService.SessionDoesNotExist()).when(this.deckStudyService)
                .study(SESSION_ID, CARD_ID, Opinion.GREEN);
        ResponseEntity<?> responseEntity = studyController.studyCard(SESSION_ID, REQUEST);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void return404_when_cardDoesNotExist() {
        doThrow(new DeckStudyService.CardDoesNotExistInTheSession()).when(this.deckStudyService)
                .study(SESSION_ID, CARD_ID, Opinion.GREEN);
        ResponseEntity<?> responseEntity = studyController.studyCard(SESSION_ID, REQUEST);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void return500Code_when_ExceptionOccured() {
        doThrow(new RuntimeException()).when(this.deckStudyService)
                .study(SESSION_ID, CARD_ID, Opinion.GREEN);
        ResponseEntity<?> responseEntity = studyController.studyCard(SESSION_ID, REQUEST);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
