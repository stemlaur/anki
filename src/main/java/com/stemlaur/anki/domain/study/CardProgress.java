package com.stemlaur.anki.domain.study;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

public final class CardProgress {
    private static final Duration MINIMUM_DURATION = Duration.of(1, SECONDS);
    private static final int GREEN_MULTIPLICAND = 10;
    private static final int ORANGE_DIVISOR = 2;
    private static final int RED_DIVISOR = 5;

    private final String cardId;
    private LocalDateTime lastEvaluationAt;
    private Duration durationBeforeNextEvaluation;

    CardProgress(final String cardId,
                 final LocalDateTime lastEvaluationAt,
                 final Duration durationBeforeNextEvaluation) {
        this.cardId = cardId;
        this.lastEvaluationAt = lastEvaluationAt;
        this.durationBeforeNextEvaluation = durationBeforeNextEvaluation;
    }

    public static CardProgress init(String cardId) {
        return new CardProgress(cardId, null, MINIMUM_DURATION);
    }

    public CardProgress updateProgress(final Opinion opinion,
                                       final LocalDateTime now) {
        this.lastEvaluationAt = now;
        switch (opinion) {
            case GREEN:
                this.durationBeforeNextEvaluation = this.durationBeforeNextEvaluation.multipliedBy(GREEN_MULTIPLICAND);
                break;
            case ORANGE:
                this.durationBeforeNextEvaluation = this.durationBeforeNextEvaluation.dividedBy(ORANGE_DIVISOR);
                break;
            default:
                this.durationBeforeNextEvaluation = this.durationBeforeNextEvaluation.dividedBy(RED_DIVISOR);
                break;
        }

        if (durationBeforeNextEvaluation.toMillis() < MINIMUM_DURATION.toMillis()) {
            this.durationBeforeNextEvaluation = MINIMUM_DURATION;
        }
        return this;
    }

    public LocalDateTime lastEvaluationAt() {
        return this.lastEvaluationAt;
    }

    public String id() {
        return this.cardId;
    }

    public Duration durationBeforeNextEvaluation() {
        return this.durationBeforeNextEvaluation;
    }
}
