package com.stemlaur.anki.domain;

import java.util.UUID;

/**
 * Domain service allowing to create a deck of card.
 */
public final class Decks {
    public Deck create(final String title) {
        return new Deck(UUID.randomUUID().toString(), title);
    }
}
