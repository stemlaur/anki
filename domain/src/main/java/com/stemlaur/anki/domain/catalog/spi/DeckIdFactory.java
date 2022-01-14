package com.stemlaur.anki.domain.catalog.spi;

import com.stemlaur.anki.domain.catalog.DeckId;

public interface DeckIdFactory {
    DeckId generate();
}
