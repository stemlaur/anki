package com.stemlaur.anki.domain.catalog.spi.fake;

import com.stemlaur.anki.domain.catalog.DeckId;
import com.stemlaur.anki.domain.catalog.spi.DeckIdFactory;

import java.util.UUID;

public class FakeDeckIdFactory implements DeckIdFactory {
    @Override
    public DeckId generate() {
        return DeckId.from(UUID.randomUUID().toString());
    }
}
