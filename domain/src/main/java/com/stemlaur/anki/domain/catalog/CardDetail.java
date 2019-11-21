package com.stemlaur.anki.domain.catalog;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
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
}
