package com.stemlaur.anki.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CardProgressServiceShould {
    private static final String CARD_TO_STUDY_ID = "1234";
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final CardProgress CARD_PROGRESS = new CardProgress(CARD_TO_STUDY_ID, NOW, Duration.ZERO);
    private CardProgressService cardProgressService;

    @Mock
    private CardProgressRepository cardProgressRepository;

    @Before
    public void setUp() {
        this.cardProgressService = new CardProgressService(this.cardProgressRepository);
    }

    @Test
    public void returnEmpty_when_CardToStudyDoesNotExist() {
        when(this.cardProgressRepository.findCardProgressById(CARD_TO_STUDY_ID)).thenReturn(empty());
        Optional<CardProgress> actual = this.cardProgressService.findByCardToStudyId(CARD_TO_STUDY_ID);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void returnCardProgress_when_ItExists() {
        when(this.cardProgressRepository.findCardProgressById(CARD_TO_STUDY_ID))
                .thenReturn(of(CARD_PROGRESS));
        Optional<CardProgress> actual = this.cardProgressService.findByCardToStudyId(CARD_TO_STUDY_ID);
        assertEquals(CARD_PROGRESS, actual.orElseThrow());
    }

    @Test
    public void saveCardProgress() {
        this.cardProgressService.save(CARD_PROGRESS);
        verify(this.cardProgressRepository, times(1)).save(CARD_PROGRESS);
    }
}