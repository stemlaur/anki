package com.stemlaur.anki.infrastructure;

import com.stemlaur.anki.domain.catalog.Deck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InMemoryDecksShould {
    private static final String DECKID = "58545437-85df-4cc7-af62-17659ca9d3ec";
    private static final String ANOTHER_DECKID = "37f07f50-72e1-40d0-9bb0-5fa240fa4479";

    private InMemoryDecks inMemoryDecks;

    @Before
    public void setUp() {
        this.inMemoryDecks = new InMemoryDecks();
    }

    @Test
    public void addADeck() {
        assertTrue(this.inMemoryDecks.find(DECKID).isEmpty());
        this.inMemoryDecks.save(new Deck(DECKID, "title"));
        assertTrue(this.inMemoryDecks.find(DECKID).isPresent());
    }

    @Test
    public void overrideDeckWhenAlreadyExist() {
        this.inMemoryDecks.save(new Deck(DECKID, "title"));
        this.inMemoryDecks.save(new Deck(DECKID, "title"));
        assertEquals(1, this.inMemoryDecks.findAll().size());
    }

    @Test
    public void deleteADeck() {
        this.inMemoryDecks.save(new Deck(DECKID, "title"));
        assertTrue(this.inMemoryDecks.find(DECKID).isPresent());

        this.inMemoryDecks.delete(DECKID);
        assertTrue(this.inMemoryDecks.find(DECKID).isEmpty());
    }

    @Test
    public void findAllDecks() {
        this.inMemoryDecks.save(new Deck(DECKID, "title"));
        this.inMemoryDecks.save(new Deck(ANOTHER_DECKID, "title"));

        assertEquals(2, this.inMemoryDecks.findAll().size());
    }
}