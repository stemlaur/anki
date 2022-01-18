package com.stemlaur.anki.domain.study;

import java.time.LocalDateTime;

public final class CardProgress {
    private static final Score MINIMUM_SCORE = new Score(1);
    private static final int GREEN_MULTIPLICAND = 10;
    private static final int ORANGE_DIVISOR = 2;
    private static final int RED_DIVISOR = 5;

    private final String cardId;
    private Score score;
    private LocalDateTime lastEvaluationAt;

    public CardProgress(final String cardId, final LocalDateTime lastEvaluationAt, final Score score) {
        this.cardId = cardId;
        this.lastEvaluationAt = lastEvaluationAt;
        this.score = score;
    }

    public static CardProgress init(String cardId) {
        return new CardProgress(cardId, null, MINIMUM_SCORE);
    }

    public CardProgress updateProgress(final Opinion opinion,
                                       final LocalDateTime now) {
        this.lastEvaluationAt = now;
        switch (opinion) {
            case GREEN:
                this.score = this.score.multipliedBy(GREEN_MULTIPLICAND);
                break;
            case ORANGE:
                this.score = this.score.dividedBy(ORANGE_DIVISOR);
                break;
            default:
                this.score = this.score.dividedBy(RED_DIVISOR);
                break;
        }
        if (score.value() < MINIMUM_SCORE.value()) {
            this.score = MINIMUM_SCORE;
        }
        return this;
    }

    LocalDateTime lastEvaluationAt() {
        return this.lastEvaluationAt;
    }

    public String id() {
        return this.cardId;
    }

    public Score score() {
        return this.score;
    }
}
