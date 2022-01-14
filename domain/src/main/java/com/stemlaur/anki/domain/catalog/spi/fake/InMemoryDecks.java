package com.stemlaur.anki.domain.catalog.spi.fake;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.spi.Decks;

import java.util.*;

public final class InMemoryDecks implements Decks {
    private final List<Deck> decks = new ArrayList<>();

    @Override
    public void save(final Deck deck) {
        this.delete(deck.idString());
        this.decks.add(deck);
    }

    @Override
    public void delete(final String id) {
        findDeckImpl(id).ifPresent(this.decks::remove);
    }

    @Override
    public Optional<Deck> find(final String id) {
        return findDeckImpl(id);
    }

    @Override
    public Collection<Deck> findAll() {
        return Collections.unmodifiableCollection(decks);
    }

    private Optional<Deck> findDeckImpl(final String id) {
        return this.decks.stream().filter(deck -> deck.idString().equals(id))
                .findFirst();
    }
}
