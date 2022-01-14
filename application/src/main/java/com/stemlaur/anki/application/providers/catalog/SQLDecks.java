package com.stemlaur.anki.application.providers.catalog;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckId;
import com.stemlaur.anki.domain.catalog.DeckTitle;
import com.stemlaur.anki.domain.catalog.spi.Decks;
import org.jooq.DSLContext;

import java.util.Collection;
import java.util.Optional;

import static com.stemlaur.anki.application.providers.jooq.Tables.DECK;

public final class SQLDecks implements Decks {
    private final DSLContext dslContext;

    public SQLDecks(final DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void save(final Deck deck) {
        this.dslContext.insertInto(DECK,
                        DECK.ID,
                        DECK.TITLE)
                .values(deck.idString(), deck.titleString())
                .execute();
    }

    @Override
    public void delete(final String id) {
        this.dslContext.delete(DECK).where(DECK.ID.eq(id));
    }

    @Override
    public Optional<Deck> find(final String id) {
        return this.dslContext.select(DECK.ID, DECK.TITLE)
                .from(DECK)
                .where(DECK.ID.eq(id))
                .stream().findFirst()
                .map(record -> new Deck(DeckId.from(record.get(DECK.ID)), new DeckTitle(record.get(DECK.TITLE))));
    }

    @Override
    public Collection<Deck> findAll() {
        return this.dslContext.fetch(DECK)
                .map(deckRecord -> new Deck(DeckId.from(deckRecord.getId()), new DeckTitle(deckRecord.getTitle())));
    }
}
