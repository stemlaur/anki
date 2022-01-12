package com.stemlaur.anki.domain.catalog;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CardDetailShould {


    @Test
    void beAValueObject() {
        CardDetail first = new CardDetail("q1", "a1");
        CardDetail second = new CardDetail("q2", "a2");

        assertEquals(first, first);
        assertNotEquals(first, second);

        assertEquals(first.hashCode(), first.hashCode());
        assertNotEquals(first.hashCode(), second.hashCode());
    }

}