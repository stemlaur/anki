package com.stemlaur.anki.domain.catalog;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DeckCreatedShould {
    private final static DeckId DECK_ID = DeckId.from("any id");

    @Test
    public void beIdentifiedByItsValue() {
        final DeckCreated first = new DeckCreated(DECK_ID, "My first title");
        final DeckCreated second = new DeckCreated(DECK_ID, "My first title");
        final DeckCreated different = new DeckCreated(DECK_ID, "My other title");

        assertEquals(first, second);
        assertNotEquals(first, different);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first.hashCode(), different.hashCode());
    }
}
