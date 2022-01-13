package com.stemlaur.anki.domain.catalog;

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

        if (question != null ? !question.equals(that.question) : that.question != null) return false;
        return answer != null ? answer.equals(that.answer) : that.answer == null;
    }

    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }
}
