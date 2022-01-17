package com.stemlaur.anki.domain.stats;

import com.stemlaur.anki.domain.catalog.DeckCreated;
import com.stemlaur.anki.domain.stats.api.IncrementDeckNumber;

import java.util.HashSet;
import java.util.Set;

public class DeckStatsService implements IncrementDeckNumber {
    private final Set<String> deckIds = new HashSet<>();

    @Override
    public void incrementByOne(final DeckCreated event) {
        deckIds.add(event.getAggregateId());
    }

    int numberOfDecks() {
        return this.deckIds.size();
    }
}
