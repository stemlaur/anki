package com.stemlaur.anki.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class CardDetail {
    private final String question;

    public CardDetail(final String question) {

        this.question = question;
    }

    public String question() {
        return this.question;
    }
}
