package com.stemlaur.anki.domain.study;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CardProgressUpdateProgressShould {
    private static final String CARD_ID = "d8394206-2f05-4177-b6c5-495dcc94fbd3";
    private static final Duration DURATION_OF_10_SECONDS = Duration.of(10, SECONDS);
    private static final Duration DURATION_OF_1_SECOND = Duration.of(1, SECONDS);
    private static final Score SCORE_OF_1 = new Score(1);
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Test
    public void updateLastEvaluationAt() {
        final CardProgress cardProgress = CardProgress.init(CARD_ID);
        cardProgress.updateProgress(Opinion.GREEN, NOW);
        assertNotNull(cardProgress.lastEvaluationAt());
    }

    @Test
    public void multiplyScoreBy10_when_opinionIsGreen() {
        final Score scoreBefore = new Score(1);

        final CardProgress cardProgress = new CardProgress(CARD_ID, NOW, scoreBefore)
                .updateProgress(Opinion.GREEN, NOW);

        final Score actual = cardProgress.score();
        assertEquals(actual, new Score(10));
    }

    @Test
    public void divideScoreBy2_when_opinionIsOrange() {
        final Score scoreBefore = new Score(10);

        final CardProgress cardProgress = new CardProgress(CARD_ID, NOW, scoreBefore);
        cardProgress.updateProgress(Opinion.ORANGE, NOW);

        final Score actual = cardProgress.score();
        assertEquals(actual, new Score(5));
    }

    @Test
    public void divideScoreBy5_when_opinionIsRed() {
        final Score scoreBefore = new Score(10);

        final CardProgress cardProgress = new CardProgress(CARD_ID, NOW, scoreBefore);
        cardProgress.updateProgress(Opinion.RED, NOW);

        final Score actual = cardProgress.score();
        assertEquals(actual, new Score(2));
    }

    @Test
    public void neverSetScoreUnder1() {
        final CardProgress cardProgressRed = new CardProgress(CARD_ID, NOW, SCORE_OF_1)
                .updateProgress(Opinion.RED, NOW);
        assertEquals(cardProgressRed.score(), SCORE_OF_1);

        final CardProgress cardProgressOrange = new CardProgress(CARD_ID, NOW, SCORE_OF_1)
                .updateProgress(Opinion.ORANGE, NOW);
        assertEquals(cardProgressOrange.score(), SCORE_OF_1);
    }
}