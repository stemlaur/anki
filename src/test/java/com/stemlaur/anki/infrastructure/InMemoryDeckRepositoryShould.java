package com.stemlaur.anki.infrastructure;

import com.stemlaur.anki.domain.Deck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InMemoryDeckRepositoryShould {

    private static final String DECKID = "1234";
    private InMemoryDeckRepository inMemoryDeckRepository;

    @Before
    public void setUp() throws Exception {
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