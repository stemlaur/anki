package com.stemlaur.anki.domain.catalog;

import com.stemlaur.anki.domain.common.AggregateRoot;
import com.stemlaur.anki.domain.common.DomainEvent;
import org.apache.commons.lang3.Validate;
import org.jmolecules.ddd.annotation.Identity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@org.jmolecules.ddd.annotation.AggregateRoot
public final class Deck implements AggregateRoot {
    @Identity
    private final DeckId id;
    private final DeckTitle title;
    private final transient List<DomainEvent> events = new ArrayList<>();
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
        this.events.add(new DeckCreated(deckId, deckTitle.getValue()));
    }

    public int addCard(final CardDetail cardDetail) {
        Validate.notNull(cardDetail);
        final int cardId = cardIdCounter;
        this.cards.add(new Card(cardId, cardDetail));
        cardIdCounter++;
        this.events.add(new CardAdded(this.id, cardId, cardDetail.question(), cardDetail.answer()));
        return cardId;
    }

    public void removeCard(final int id) {
        this.cards = this.cards.stream().filter(c -> c.id() != id).collect(Collectors.toList());
    }

    public String idString() {
        return this.id.getValue();
    }

    public String titleString() {
        return this.title.getValue();
    }

    public List<Card> cards() {
        return Collections.unmodifiableList(this.cards);
    }

    @Override
    public List<DomainEvent> events() {
        List<DomainEvent> domainEvents = new ArrayList<>(this.events);
        this.events.clear();
        return domainEvents;
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
