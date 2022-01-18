package com.stemlaur.anki.domain.catalog;

import java.util.Objects;

public final class CardDetail {
    private final String question;
    private final String answer;

    public CardDetail(final String question, final String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String question() {
        return this.question;
    }

    public String answer() {
        return this.answer;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CardDetail that = (CardDetail) o;

        if (!Objects.equals(question, that.question)) return false;
        return Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }
}
