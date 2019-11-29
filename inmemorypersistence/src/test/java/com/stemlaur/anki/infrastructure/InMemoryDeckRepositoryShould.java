package com.stemlaur.anki.infrastructure;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.infrastructure.InMemoryDeckRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InMemoryDeckRepositoryShould {
    private static final String DECKID = "58545437-85df-4cc7-af62-17659ca9d3ec";
    private static final String ANOTHER_DECKID = "37f07f50-72e1-40d0-9bb0-5fa240fa4479";

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
    public void overrideDeckWhenAlreadyExist() {
        this.inMemoryDeckRepository.save(new Deck(DECKID, "title"));
        this.inMemoryDeckRepository.save(new Deck(DECKID, "title"));
        assertEquals(1, this.inMemoryDeckRepository.findAll().size());
    }

    @Test
    public void deleteADeck() {
        this.inMemoryDeckRepository.save(new Deck(DECKID, "title"));
        assertTrue(this.inMemoryDeckRepository.findDeckById(DECKID).isPresent());

        this.inMemoryDeckRepository.delete(DECKID);
        assertTrue(this.inMemoryDeckRepository.findDeckById(DECKID).isEmpty());
    }

    @Test
    public void findAllDecks() {
        this.inMemoryDeckRepository.save(new Deck(DECKID, "title"));
        this.inMemoryDeckRepository.save(new Deck(ANOTHER_DECKID, "title"));

        assertEquals(2, this.inMemoryDeckRepository.findAll().size());
    }
}