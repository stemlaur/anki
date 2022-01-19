/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
