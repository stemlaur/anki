package com.stemlaur.anki.infrastructure;

import com.stemlaur.anki.domain.study.CardProgress;
import com.stemlaur.anki.infrastructure.InMemoryCardProgressRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class InMemoryCardProgressRepositoryShould {
    private static final String CARD_ID = "b56a78e1-cd2a-4b6b-b7af-d38cfdaef557";
    private InMemoryCardProgressRepository inMemoryCardProgressRepository;

    @Before
    public void setUp() {
        this.inMemoryCardProgressRepository = new InMemoryCardProgressRepository();
    }

    @Test
    public void saveCardProgress() {
        assertTrue(this.inMemoryCardProgressRepository.findCardProgressById(CARD_ID).isEmpty());

        this.inMemoryCardProgressRepository.save(CardProgress.init(CARD_ID));

        assertTrue(this.inMemoryCardProgressRepository.findCardProgressById(CARD_ID).isPresent());
    }
}
