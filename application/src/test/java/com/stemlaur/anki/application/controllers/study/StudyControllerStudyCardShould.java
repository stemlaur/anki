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

import com.stemlaur.anki.domain.study.CardDoesNotExistInTheSession;
import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.Opinion;
import com.stemlaur.anki.domain.study.SessionDoesNotExist;
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
        doThrow(new SessionDoesNotExist(SESSION_ID)).when(this.deckStudyService)
                .study(SESSION_ID, CARD_ID, Opinion.GREEN);
        ResponseEntity<?> responseEntity = studyController.studyCard(SESSION_ID, REQUEST);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void return404_when_cardDoesNotExist() {
        doThrow(new CardDoesNotExistInTheSession(SESSION_ID, CARD_ID)).when(this.deckStudyService)
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
