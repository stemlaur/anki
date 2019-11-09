package com.stemlaur.anki.domain;

import com.stemlaur.anki.domain.common.Tuple;
import com.stemlaur.anki.domain.common.TupleWithMultipleEvents;

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
        final Tuple<DeckCreated, Deck> tuple = Deck.create(generatedId, title);
        this.deckRepository.save(tuple.getAggregate());
        return generatedId;
    }

    public void remove(final String deckId) {
        this.deckRepository.delete(deckId);
    }
}
