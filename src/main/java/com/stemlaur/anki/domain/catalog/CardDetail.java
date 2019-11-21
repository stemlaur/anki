package com.stemlaur.anki.domain.catalog;

import lombok.EqualsAndHashCode;

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
}
