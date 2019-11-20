package com.stemlaur.anki.domain.study;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CardProgressUpdateProgressShould {

    private static final String CARD_ID = "435466";
    public static final Duration DURATION_OF_10_SECONDS = Duration.of(10, SECONDS);
    public static final LocalDateTime NOW = LocalDateTime.now();

    @Test
    public void updateLastEvaluationAt() {
        final CardProgress cardProgress = CardProgress.init(CARD_ID);
        cardProgress.updateProgress(Opinion.GREEN, NOW);
        assertNotNull(cardProgress.lastEvaluationAt());
    }

    @Test
    public void multiplyDurationBeforeNextEvaluationBy10_when_opinionIsGreen() {
        final Duration durationBeforeStudy = DURATION_OF_10_SECONDS;

        final CardProgress cardProgress = new CardProgress(CARD_ID, NOW, durationBeforeStudy);
        cardProgress.updateProgress(Opinion.GREEN, NOW);

        final Duration actual = cardProgress.durationBeforeNextEvaluation();
        assertEquals(actual.toMillis(), durationBeforeStudy.toMillis() * 10);
    }

    @Test
    public void divideDurationBeforeNextEvaluationBy2_when_opinionIsOrange() {
        final Duration durationBeforeStudy = DURATION_OF_10_SECONDS;

        final CardProgress cardProgress = new CardProgress(CARD_ID, NOW, durationBeforeStudy);
        cardProgress.updateProgress(Opinion.ORANGE, NOW);

        final Duration actual = cardProgress.durationBeforeNextEvaluation();
        assertEquals(actual.toMillis(), durationBeforeStudy.toMillis() / 2);
    }

    @Test
    public void divideDurationBeforeNextEvaluationBy5_when_opinionIsRed() {
        final Duration durationBeforeStudy = DURATION_OF_10_SECONDS;

        final CardProgress cardProgress = new CardProgress(CARD_ID, NOW, durationBeforeStudy);
        cardProgress.updateProgress(Opinion.RED, NOW);

        final Duration actual = cardProgress.durationBeforeNextEvaluation();
        assertEquals(actual.toMillis(), durationBeforeStudy.toMillis() / 5);
    }
}