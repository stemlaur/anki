package com.stemlaur.anki.domain.catalog;

import com.stemlaur.anki.domain.catalog.api.AddCard;
import com.stemlaur.anki.domain.catalog.api.CreateDeck;
import com.stemlaur.anki.domain.catalog.api.FindDecks;
import com.stemlaur.anki.domain.catalog.api.RemoveDeck;
import com.stemlaur.anki.domain.catalog.spi.DeckIdFactory;
import com.stemlaur.anki.domain.catalog.spi.Decks;
import com.stemlaur.anki.domain.common.spi.DomainEvents;
import org.jmolecules.ddd.annotation.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class DeckService implements CreateDeck, RemoveDeck, AddCard, FindDecks {
    private final Decks decks;
    private final DeckIdFactory deckIdFactory;
    private final DomainEvents domainEvents;

    public DeckService(final Decks decks,
                       final DeckIdFactory deckIdFactory,
                       final DomainEvents domainEvents) {

        this.decks = decks;
        this.deckIdFactory = deckIdFactory;
        this.domainEvents = domainEvents;
    }

    @Override
    public String create(final String title) {
        final DeckId generatedId = this.deckIdFactory.generate();
        final Deck deck = new Deck(generatedId, new DeckTitle(title));
        this.decks.save(deck);
        this.domainEvents.publish(deck.events());
        return generatedId.getValue();
    }

    @Override
    public void remove(final String deckId) {
        this.decks.find(deckId).orElseThrow(() -> new DeckDoesNotExist(deckId));
        this.decks.delete(deckId);
    }

    @Override
    public void addCard(final String deckId, final CardDetail cardDetail) {
        final Deck deck = this.decks.find(deckId).orElseThrow(() -> new DeckDoesNotExist(deckId));
        deck.addCard(cardDetail);
        this.decks.save(deck);

        this.domainEvents.publish(deck.events());
    }

    @Override
    public Optional<Deck> byId(final String deckId) {
        return this.decks.find(deckId);
    }

    @Override
    public Collection<Deck> all() {
        return this.decks.findAll();
    }

    //@formatter:on
}
