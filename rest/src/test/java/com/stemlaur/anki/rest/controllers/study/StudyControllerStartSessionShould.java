package com.stemlaur.anki.rest.controllers.study;

import com.stemlaur.anki.domain.study.DeckStudyService;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudyControllerStartSessionShould {

    private static final String DECK_ID = "0501616b-9f3c-43f0-b869-c9dbec9429f4";
    private static final String SESSION_ID = "9b9dc6dd-18d3-45e4-92c1-399253ec954d";
    private StudyController studyController;
    @Mock
    private DeckStudyService deckStudyService;

    @BeforeEach
    public void setUp() {
        this.studyController = new StudyController(deckStudyService);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void createStudySession() {
        when(this.deckStudyService.startStudySession(DECK_ID)).thenReturn(SESSION_ID);
        ResponseEntity<String> responseEntity = studyController.createStudySession(new CreateStudySessionRequest(DECK_ID));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/" + SESSION_ID);
        assertThat(responseEntity.getBody()).isEqualTo(SESSION_ID);
        verify(this.deckStudyService, times(1)).startStudySession(DECK_ID);
    }

    @Test
    public void return500Code_when_ExceptionOccured() {
        when(this.deckStudyService.startStudySession(DECK_ID)).thenThrow(new RuntimeException());
        ResponseEntity<String> responseEntity = studyController.createStudySession(new CreateStudySessionRequest(DECK_ID));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
