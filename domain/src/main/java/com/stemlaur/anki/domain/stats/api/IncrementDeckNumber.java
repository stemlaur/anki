package com.stemlaur.anki.domain.stats.api;

import com.stemlaur.anki.domain.catalog.DeckCreated;

public interface IncrementDeckNumber {
    void incrementByOne(final DeckCreated FIRST_DECK_CREATED);
}
