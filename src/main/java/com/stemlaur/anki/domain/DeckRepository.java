package com.stemlaur.anki.domain;

public interface DeckRepository {
    void save(Deck deck);

    void delete(String id);
}