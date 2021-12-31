package com.stemlaur.anki.domain.catalog;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Domain service allowing to create a deck of card.
 */
public class DeckService {
    private final Decks decks;

    public DeckService(final Decks decks) {

        this.decks = decks;
    }

    public String create(final String title) {
        final String generatedId = UUID.randomUUID().toString();
        final Deck deck = new Deck(generatedId, title);
        this.decks.save(deck);
        return generatedId;
    }

    public void remove(final String deckId) {
        this.decks.find(deckId).orElseThrow(DeckDoesNotExist::new);
        this.decks.delete(deckId);
    }

    public void addCard(final String deckId, final CardDetail cardDetail) {
        final Deck deck = this.decks.find(deckId).orElseThrow(DeckDoesNotExist::new);
        deck.addCard(cardDetail);
        this.decks.save(deck);
    }

    public Optional<Deck> findDeckById(final String deckId) {
        return this.decks.find(deckId);
    }

    public Collection<Deck> findAll() {
        return this.decks.findAll();
    }

    //@formatter:on
}
