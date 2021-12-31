package com.stemlaur.anki.domain.catalog;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Deck {
    private final DeckId id;
    private final DeckTitle title;
    private List<Card> cards = new ArrayList<>();
    private int cardIdCounter = 1;

    public Deck(DeckId deckId, DeckTitle deckTitle) {
        this.id = deckId;
        this.title = deckTitle;
        if (deckId == null) {
            throw new DeckIdIsRequired();
        }
        if (deckTitle == null) {
            throw new DeckTitleIsRequired();
        }
    }

    public int addCard(final CardDetail cardDetail) {
        Validate.notNull(cardDetail);
        final int id = cardIdCounter;
        this.cards.add(new Card(id, cardDetail));
        cardIdCounter++;
        return id;
    }

    public void removeCard(final int id) {
        this.cards = this.cards.stream().filter(c -> c.id() != id).collect(Collectors.toList());
    }

    public String id() {
        return this.id.getValue();
    }

    public List<Card> cards() {
        return Collections.unmodifiableList(this.cards);
    }

    public String title() {
        return this.title.getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Deck deck = (Deck) o;

        return id.equals(deck.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
