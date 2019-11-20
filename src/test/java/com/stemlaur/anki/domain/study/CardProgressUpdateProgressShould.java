package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.study.CardProgress;
import com.stemlaur.anki.domain.study.Opinion;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class CardProgressUpdateProgressShould {

    private static final String CARD_ID = "435466";

    @Test
    public void updateLastEvaluationAt() {
        final CardProgress cardProgress = CardProgress.init(CARD_ID);
        cardProgress.updateProgress(Opinion.GREEN, LocalDateTime.now());
        assertNotNull(cardProgress.lastEvaluationAt());
    }
}