package com.stemlaur.anki.domain;

import java.util.UUID;

/**
 * Domain service allowing to create a deck of card.
 */
public final class Decks {
    private final DeckRepository deckRepository;

    public Decks(final DeckRepository deckRepository) {

        this.deckRepository = deckRepository;
    }

    public String create(final String title) {
        final String generatedId = UUID.randomUUID().toString();
        final Deck deck = new Deck(generatedId, title);
        this.deckRepository.save(deck);
        return generatedId;
    }
}
