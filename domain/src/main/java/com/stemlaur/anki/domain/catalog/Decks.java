package com.stemlaur.anki.domain.catalog;

import java.util.Collection;
import java.util.Optional;

public interface Decks {
    void save(Deck deck);

    void delete(String id);

    Optional<Deck> find(String id);

    Collection<Deck> findAll();
}
