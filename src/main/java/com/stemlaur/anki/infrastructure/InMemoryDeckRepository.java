package com.stemlaur.anki.infrastructure;

import com.stemlaur.anki.domain.Deck;
import com.stemlaur.anki.domain.DeckRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class InMemoryDeckRepository implements DeckRepository {
    private final List<Deck> decks = new ArrayList<>();

    @Override
    public void save(final Deck deck) {
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

    private Optional<Deck> findDeck(final String id) {
        return this.decks.stream().filter(deck -> deck.id().equals(id))
                .findFirst();
    }
}
