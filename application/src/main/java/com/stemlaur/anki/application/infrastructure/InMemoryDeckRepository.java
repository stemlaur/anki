package com.stemlaur.anki.application.infrastructure;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckRepository;

import java.util.*;

public final class InMemoryDeckRepository implements DeckRepository {
    private final List<Deck> decks = new ArrayList<>();

    @Override
    public void save(final Deck deck) {
        this.delete(deck.id());
        this.decks.add(deck);
    }

    @Override
    public void delete(final String id) {
        findDeck(id).ifPresent(this.decks::remove);
    }

    @Override
    public Optional<Deck> findDeckById(final String id) {
        return findDeck(id);
    }

    @Override
    public Collection<Deck> findAll() {
        return Collections.unmodifiableCollection(decks);
    }

    private Optional<Deck> findDeck(final String id) {
        return this.decks.stream().filter(deck -> deck.id().equals(id))
                .findFirst();
    }
}
