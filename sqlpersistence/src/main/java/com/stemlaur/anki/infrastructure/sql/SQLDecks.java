package com.stemlaur.anki.infrastructure.sql;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.Decks;
import org.jooq.DSLContext;

import java.util.Collection;
import java.util.Optional;

import static com.stemlaur.anki.sqlpersistence.jooq.db.public_.Tables.DECK;

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
                .values(deck.id(), deck.title())
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
                .map(record -> new Deck(record.get(DECK.ID), record.get(DECK.TITLE)));
    }

    @Override
    public Collection<Deck> findAll() {
        return this.dslContext.fetch(DECK)
                .map(deckRecord -> new Deck(deckRecord.getId(), deckRecord.getTitle()));
    }
}
