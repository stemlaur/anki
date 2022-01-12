package com.stemlaur.anki.domain.study;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ScoreShould {


    @Test
    void beAValueObject() {
        Score first = new Score(1);
        Score second = new Score(2);

        assertEquals(first, first);
        assertNotEquals(first, second);

        assertEquals(first.hashCode(), first.hashCode());
        assertNotEquals(first.hashCode(), second.hashCode());
    }
}