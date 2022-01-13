package com.stemlaur.anki.domain.catalog;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckIdShould {

    @Test
    void beAValueObject() {
        DeckId firstDeckId = DeckId.from("1234");
        DeckId secondDeckId = DeckId.from("7980");

        assertEquals(firstDeckId, firstDeckId);
        assertNotEquals(firstDeckId, secondDeckId);

        assertEquals(firstDeckId.hashCode(), firstDeckId.hashCode());
        assertNotEquals(firstDeckId.hashCode(), secondDeckId.hashCode());

        assertEquals(firstDeckId.getValue(), firstDeckId.toString());
    }
}
