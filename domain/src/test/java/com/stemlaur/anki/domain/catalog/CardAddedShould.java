package com.stemlaur.anki.domain.catalog;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CardAddedShould {
    private final static DeckId DECK_ID = DeckId.from("any id");

    @Test
    public void beIdentifiedByItsValue() {
        final CardAdded first = new CardAdded(DECK_ID, 1, "Question 1", "Answer 1");
        final CardAdded second = new CardAdded(DECK_ID, 1, "Question 1", "Answer 1");
        final CardAdded different = new CardAdded(DECK_ID, 2, "Question 2", "Answer 2");

        assertEquals(first, second);
        assertNotEquals(first, different);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first.hashCode(), different.hashCode());
    }
}
