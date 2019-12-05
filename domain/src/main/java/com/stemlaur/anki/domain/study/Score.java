package com.stemlaur.anki.domain.study;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.Validate;

@EqualsAndHashCode
public final class Score {
    private final int score;

    public Score(final int score) {
        this.score = score;
    }

    public Score multipliedBy(final int multiplicand) {
        return new Score(score * multiplicand);
    }

    public Score dividedBy(final int divisor) {
        Validate.isTrue(divisor > 0);
        return new Score(score / divisor);
    }

    public int value() {
        return score;
    }
}
