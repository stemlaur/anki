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
package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.study.spi.CardProgresses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardProgressServiceShould {
    private static final String CARD_TO_STUDY_ID = "f0a29b14-a5c7-4dae-8b9f-f3ca7b7c9b2a";
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final CardProgress CARD_PROGRESS = new CardProgress(CARD_TO_STUDY_ID, NOW, new Score(1));

    private CardProgressService cardProgressService;
    @Mock
    private CardProgresses cardProgresses;

    @BeforeEach
    public void setUp() {
        this.cardProgressService = new CardProgressService(this.cardProgresses);
    }

    @Test
    public void returnNewCardProgress_when_CardToStudyDoesNotExist() {
        when(this.cardProgresses.findCardProgressById(CARD_TO_STUDY_ID)).thenReturn(empty());
        CardProgress actual = this.cardProgressService.findByCardToStudyId(CARD_TO_STUDY_ID);
        assertNotNull(actual);
    }

    @Test
    public void returnCardProgress_when_ItExists() {
        when(this.cardProgresses.findCardProgressById(CARD_TO_STUDY_ID))
                .thenReturn(of(CARD_PROGRESS));
        CardProgress actual = this.cardProgressService.findByCardToStudyId(CARD_TO_STUDY_ID);
        assertEquals(CARD_PROGRESS, actual);
    }

    @Test
    public void saveCardProgress() {
        this.cardProgressService.save(CARD_PROGRESS);
        verify(this.cardProgresses, times(1)).save(CARD_PROGRESS);
    }
}
