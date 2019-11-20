package com.stemlaur.anki.domain;

import java.time.Duration;
import java.time.LocalDateTime;

public final class CardProgress {
    private final String cardId;
    private LocalDateTime lastEvaluationAt;
    private Duration durationBeforeNextEvaluation;

    CardProgress(final String cardId, final LocalDateTime lastEvaluationAt, final Duration durationBeforeNextEvaluation) {
        this.cardId = cardId;
        this.lastEvaluationAt = lastEvaluationAt;
        this.durationBeforeNextEvaluation = durationBeforeNextEvaluation;
    }

    public static CardProgress init(String cardId) {
        return new CardProgress(cardId, null, Duration.ZERO);
    }

    public void updateProgress(final Opinion opinion,
                               LocalDateTime now) {
        this.lastEvaluationAt = now;
    }

    public LocalDateTime lastEvaluationAt() {
        return this.lastEvaluationAt;
    }

    public Duration durationBeforeNextEvaluation() {
        return this.durationBeforeNextEvaluation;
    }

    public String id() {
        return this.cardId;
    }
}
