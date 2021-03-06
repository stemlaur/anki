package com.stemlaur.anki.domain.catalog;

import org.junit.Test;

import static org.junit.Assert.*;

public class CardShould {
    private static final int CARD_ID = 1;
    private static final int ANOTHER_CARD_ID = 2;
    @Test
    public void beIdentifiedAsAValueObject() {
        final Card firstCard = new Card(CARD_ID, new CardDetail("question 1", "answer 1"));
        final Card secondCard = new Card(CARD_ID, new CardDetail("question 1", "answer 1"));
        final Card differentCard = new Card(ANOTHER_CARD_ID, new CardDetail("question 2", "answer 2"));

        assertEquals(firstCard, secondCard);
        assertNotEquals(firstCard, differentCard);
    }
}