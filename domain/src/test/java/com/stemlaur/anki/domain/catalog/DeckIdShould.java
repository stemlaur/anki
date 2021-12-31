package com.stemlaur.anki.domain.catalog;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckIdShould {

    @Test
    void beAValueObject() {
        DeckId firstDeckId = DeckId.of();
        DeckId secondDeckId = DeckId.of();

        assertEquals(firstDeckId, firstDeckId);
        assertNotEquals(firstDeckId, secondDeckId);

        assertEquals(firstDeckId.hashCode(), firstDeckId.hashCode());
        assertNotEquals(firstDeckId.hashCode(), secondDeckId.hashCode());

        assertEquals(firstDeckId.getValue(), firstDeckId.toString());
    }
}