package com.stemlaur.anki.domain.catalog;

import com.stemlaur.anki.domain.catalog.spi.DeckIdFactory;

public class DumbDeckIdFactory implements DeckIdFactory {
    private String fixedId;

    public DumbDeckIdFactory(final String fixedId) {
        this.fixedId = fixedId;
    }

    public void changedFixedId(final String fixedId) {
        this.fixedId = fixedId;
    }

    @Override
    public DeckId generate() {
        return DeckId.from(fixedId);
    }
}
