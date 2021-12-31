package com.stemlaur.anki.domain.catalog;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DeckTitleShould {

    @Test
    void beAValueObject() {
        DeckTitle firstDeckTitle = new DeckTitle("my first title");
        DeckTitle secondDeckTitle = new DeckTitle("my second title");

        assertEquals(firstDeckTitle, firstDeckTitle);
        assertNotEquals(firstDeckTitle, secondDeckTitle);

        assertEquals(firstDeckTitle.hashCode(), firstDeckTitle.hashCode());
        assertNotEquals(firstDeckTitle.hashCode(), secondDeckTitle.hashCode());

        assertEquals("my first title", firstDeckTitle.toString());
    }

}