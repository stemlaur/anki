package com.stemlaur.anki.domain.catalog.fake;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckId;
import com.stemlaur.anki.domain.catalog.DeckTitle;
import com.stemlaur.anki.domain.catalog.spi.fake.InMemoryDecks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryDecksShould {
    private static final DeckId DECK_ID = DeckId.from("any id");
    private static final DeckId ANOTHER_DECK_ID = DeckId.from("any other id");

    private InMemoryDecks inMemoryDecks;

    @BeforeEach
    public void setUp() {
        this.inMemoryDecks = new InMemoryDecks();
    }

    @Test
    public void addADeck() {
        assertTrue(this.inMemoryDecks.find(DECK_ID.getValue()).isEmpty());
        this.inMemoryDecks.save(new Deck(DECK_ID, new DeckTitle("title")));
        assertTrue(this.inMemoryDecks.find(DECK_ID.getValue()).isPresent());
    }

    @Test
    public void overrideDeckWhenAlreadyExist() {
        this.inMemoryDecks.save(new Deck(DECK_ID, new DeckTitle("title")));
        this.inMemoryDecks.save(new Deck(DECK_ID, new DeckTitle("title")));
        assertEquals(1, this.inMemoryDecks.findAll().size());
    }

    @Test
    public void deleteADeck() {
        this.inMemoryDecks.save(new Deck(DECK_ID, new DeckTitle("title")));
        assertTrue(this.inMemoryDecks.find(DECK_ID.getValue()).isPresent());

        this.inMemoryDecks.delete(DECK_ID.getValue());
        assertTrue(this.inMemoryDecks.find(DECK_ID.getValue()).isEmpty());
    }

    @Test
    public void findAllDecks() {
        this.inMemoryDecks.save(new Deck(DECK_ID, new DeckTitle("title")));
        this.inMemoryDecks.save(new Deck(ANOTHER_DECK_ID, new DeckTitle("title")));

        assertEquals(2, this.inMemoryDecks.findAll().size());
    }
}
