package com.stemlaur.anki.domain.study;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

public final class CardProgress {
    private static final Duration MINIMUM_DURATION = Duration.of(1, SECONDS);
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
        if (opinion == Opinion.GREEN) {
            this.durationBeforeNextEvaluation = this.durationBeforeNextEvaluation.multipliedBy(10);
        } else if (opinion == Opinion.ORANGE) {
            this.durationBeforeNextEvaluation = this.durationBeforeNextEvaluation.dividedBy(2);
        } else {
            this.durationBeforeNextEvaluation = this.durationBeforeNextEvaluation.dividedBy(5);
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
