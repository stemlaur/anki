package com.stemlaur.anki.domain.catalog;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Card card = (Card) o;

        return id == card.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
