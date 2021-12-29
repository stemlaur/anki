package com.stemlaur.anki.infrastructure;

import com.stemlaur.anki.domain.study.CardProgress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryCardProgressesShould {
    private static final String CARD_ID = "b56a78e1-cd2a-4b6b-b7af-d38cfdaef557";
    private InMemoryCardProgresses inMemoryCardProgresses;

    @BeforeEach
    public void setUp() {
        this.inMemoryCardProgresses = new InMemoryCardProgresses();
    }

    @Test
    public void saveCardProgress() {
        assertTrue(this.inMemoryCardProgresses.findCardProgressById(CARD_ID).isEmpty());

        this.inMemoryCardProgresses.save(CardProgress.init(CARD_ID));

        assertTrue(this.inMemoryCardProgresses.findCardProgressById(CARD_ID).isPresent());
    }
}
