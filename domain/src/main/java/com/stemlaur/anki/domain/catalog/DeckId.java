package com.stemlaur.anki.domain.catalog;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

public class DeckId {
    private final String value;

    private DeckId(String value) {
        this.value = value;
    }

    public static DeckId of() {
        return new DeckId(UUID.randomUUID().toString());
    }

    public static DeckId from(final String id) {
        return new DeckId(id);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DeckId deckId = (DeckId) o;

        return new EqualsBuilder().append(value, deckId.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
