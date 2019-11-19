package com.stemlaur.anki.domain;

import com.stemlaur.anki.infrastructure.InMemoryDeckRepository;

import java.util.UUID;

/**
 * Domain service allowing to create a deck of card.
 */
public final class DeckService {
    private final DeckRepository deckRepository;

    public DeckService() {
        this(new InMemoryDeckRepository());
    }

    public DeckService(final DeckRepository deckRepository) {

        this.deckRepository = deckRepository;
    }

    public String create(final String contributorId, final String title) {
        final String generatedId = UUID.randomUUID().toString();
        final Deck deck = new Deck(generatedId, title);
        this.deckRepository.save(deck);
        return generatedId;
    }

    public void remove(final String deckId) {
        this.deckRepository.findDeckById(deckId).orElseThrow(DeckDoesNotExist::new);
        this.deckRepository.delete(deckId);
    }

    public void addCard(final String contributorId, final String deckId, final CardDetail cardDetail) {
        final Deck deck = this.deckRepository.findDeckById(deckId).orElseThrow(DeckDoesNotExist::new);
        deck.addCard(cardDetail);
        this.deckRepository.save(deck);
    }

    //@formatter:off
    public static class DeckDoesNotExist extends RuntimeException { }
    //@formatter:on
}
