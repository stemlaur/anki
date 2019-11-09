package com.stemlaur.anki.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Card {
    private final String question;

    public Card(final String question) {

        this.question = question;
    }
}
