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

import com.stemlaur.anki.application.controllers.model.NextCardToStudyResponse;
import com.stemlaur.anki.domain.study.CardToStudy;
import com.stemlaur.anki.domain.study.SessionDoesNotExist;
import com.stemlaur.anki.domain.study.api.StudyDeck;
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
    private StudyDeck deckStudyService;

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
                studyController.nextCardToStudy(SESSION_ID).getBody();
        assertThat(responseEntity).isEqualTo(new NextCardToStudyResponse().id(CARD_ID).question(A_QUESTION).answer(AN_ANSWER));
    }

    @Test
    public void return404_when_notPresent() {
        when(this.deckStudyService.nextCardToStudy(SESSION_ID)).thenReturn(empty());
        ResponseEntity<?> responseEntity = studyController.nextCardToStudy(SESSION_ID);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void return404_when_sessionDoesNotExist() {
        when(this.deckStudyService.nextCardToStudy(SESSION_ID)).thenThrow(new SessionDoesNotExist(SESSION_ID));
        ResponseEntity<?> responseEntity = studyController.nextCardToStudy(SESSION_ID);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void return500Code_when_ExceptionOccurred() {
        when(this.deckStudyService.nextCardToStudy(SESSION_ID)).thenThrow(new RuntimeException());
        ResponseEntity<?> responseEntity = studyController.nextCardToStudy(SESSION_ID);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
