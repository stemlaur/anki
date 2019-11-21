package com.stemlaur.anki.domain.catalog;

import java.util.Optional;

public interface DeckRepository {
    void save(Deck deck);

    void delete(String id);

    Optional<Deck> findDeckById(String id);
}
