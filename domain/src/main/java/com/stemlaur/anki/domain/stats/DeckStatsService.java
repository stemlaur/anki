package com.stemlaur.anki.domain.stats;

import com.stemlaur.anki.domain.catalog.DeckCreated;
import com.stemlaur.anki.domain.stats.api.IncrementDeckNumber;
import org.jmolecules.ddd.annotation.Service;

import java.util.HashSet;
import java.util.Set;

@Service
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
