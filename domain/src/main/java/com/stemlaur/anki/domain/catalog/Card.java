package com.stemlaur.anki.domain.catalog;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Card {
    private final int id;
    private final CardDetail detail;

    Card(final int id, final CardDetail detail) {
        this.id = id;
        this.detail = detail;
    }

    public int id() {
        return this.id;
    }

    public CardDetail detail() {
        return this.detail;
    }
}
