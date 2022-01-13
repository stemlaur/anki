package com.stemlaur.anki.domain.catalog.api;

import com.stemlaur.anki.domain.catalog.Deck;

import java.util.Collection;
import java.util.Optional;

public interface FindDecks {
    Optional<Deck> byId(final String deckId);

    Collection<Deck> all();
}
