package com.stemlaur.anki.domain.study;

import org.apache.commons.lang3.Validate;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Score score1 = (Score) o;

        return score == score1.score;
    }

    @Override
    public int hashCode() {
        return score;
    }
}
