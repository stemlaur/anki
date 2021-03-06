package com.stemlaur.anki.domain.study;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CardProgressServiceShould {
    private static final String CARD_TO_STUDY_ID = "f0a29b14-a5c7-4dae-8b9f-f3ca7b7c9b2a";
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final CardProgress CARD_PROGRESS = new CardProgress(CARD_TO_STUDY_ID, NOW, new Score(1));

    private CardProgressService cardProgressService;
    @Mock
    private CardProgresses cardProgresses;

    @Before
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