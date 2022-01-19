/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.stemlaur.anki.application.controllers.study;

import com.stemlaur.anki.application.controllers.study.CreateStudySessionRequest;
import com.stemlaur.anki.application.controllers.study.StudyController;
import com.stemlaur.anki.domain.study.api.StudyDeck;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyControllerStartSessionShould {

    private static final String DECK_ID = "0501616b-9f3c-43f0-b869-c9dbec9429f4";
    private static final String SESSION_ID = "9b9dc6dd-18d3-45e4-92c1-399253ec954d";
    private StudyController studyController;
    @Mock
    private StudyDeck deckStudyService;

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
