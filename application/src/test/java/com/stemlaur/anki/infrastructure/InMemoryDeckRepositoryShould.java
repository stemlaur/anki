package com.stemlaur.anki.infrastructure;

import com.stemlaur.anki.domain.catalog.Deck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InMemoryDeckRepositoryShould {
    private static final String DECKID = "58545437-85df-4cc7-af62-17659ca9d3ec";

    private InMemoryDeckRepository inMemoryDeckRepository;

    @Before
    public void setUp() {
        this.inMemoryDeckRepository = new InMemoryDeckRepository();
    }

    @Test
    public void addADeck() {
        assertTrue(this.inMemoryDeckRepository.findDeckById(DECKID).isEmpty());
        this.inMemoryDeckRepository.save(new Deck(DECKID, "title"));
        assertTrue(this.inMemoryDeckRepository.findDeckById(DECKID).isPresent());
    }

    @Test
    public void deleteADeck() {
        this.inMemoryDeckRepository.save(new Deck(DECKID, "title"));
        assertTrue(this.inMemoryDeckRepository.findDeckById(DECKID).isPresent());

        this.inMemoryDeckRepository.delete(DECKID);
        assertTrue(this.inMemoryDeckRepository.findDeckById(DECKID).isEmpty());
    }
}