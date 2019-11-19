package com.stemlaur.anki.domain;

import java.util.UUID;

/**
 * Domain service allowing to create a deck of card.
 */
public final class DeckService {
    private final DeckRepository deckRepository;

    public DeckService(final DeckRepository deckRepository) {

        this.deckRepository = deckRepository;
    }

    public String create(final String title) {
        final String generatedId = UUID.randomUUID().toString();
        final Deck deck = Deck.create(generatedId, title);
        this.deckRepository.save(deck);
        return generatedId;
    }

    public void remove(final String deckId) {
        this.deckRepository.delete(deckId);
    }
}
