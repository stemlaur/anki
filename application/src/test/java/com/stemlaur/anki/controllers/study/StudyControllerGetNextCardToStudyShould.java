package com.stemlaur.anki.controllers.study;

import com.stemlaur.anki.domain.study.CardToStudy;
import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.DeckStudyService.SessionDoesNotExist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudyControllerGetNextCardToStudyShould {
    private static final String SESSION_ID = "9b9dc6dd-18d3-45e4-92c1-399253ec954d";
    private static final String CARD_ID = "8e3760ec-013a-42b3-aa20-5b5f1506ca58";
    private static final String A_QUESTION = "A Question";
    private static final String AN_ANSWER = "An Answer";
    private static final CardToStudy CARD_TO_STUDY = new CardToStudy(CARD_ID, A_QUESTION, AN_ANSWER);

    private StudyController studyController;
    @Mock
    private DeckStudyService deckStudyService;

    @BeforeEach
    public void setUp() {
        this.studyController = new StudyController(deckStudyService);
    }

    @Test
    public void return200_when_isPresent() {
        when(this.deckStudyService.nextCardToStudy(SESSION_ID)).thenReturn(of(CARD_TO_STUDY));
        ResponseEntity<?> responseEntity = studyController.nextCardToStudy(SESSION_ID);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        verify(this.deckStudyService, times(1)).nextCardToStudy(SESSION_ID);
    }

    @Test
    public void returnCardToStudy_when_isPresent() {
        when(this.deckStudyService.nextCardToStudy(SESSION_ID)).thenReturn(of(CARD_TO_STUDY));
        NextCardToStudyResponse responseEntity =
                (NextCardToStudyResponse) studyController.nextCardToStudy(SESSION_ID).getBody();
        assertThat(responseEntity).isEqualTo(new NextCardToStudyResponse(CARD_ID, A_QUESTION, AN_ANSWER));
    }

    @Test
    public void return404_when_notPresent() {
        when(this.deckStudyService.nextCardToStudy(SESSION_ID)).thenReturn(empty());
        ResponseEntity<?> responseEntity = studyController.nextCardToStudy(SESSION_ID);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void return404_when_sessionDoesNotExist() {
        when(this.deckStudyService.nextCardToStudy(SESSION_ID)).thenThrow(new SessionDoesNotExist());
        ResponseEntity<?> responseEntity = studyController.nextCardToStudy(SESSION_ID);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void return500Code_when_ExceptionOccured() {
        when(this.deckStudyService.nextCardToStudy(SESSION_ID)).thenThrow(new RuntimeException());
        ResponseEntity<?> responseEntity = studyController.nextCardToStudy(SESSION_ID);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
