package com.stemlaur.anki.domain.catalog;

import com.stemlaur.anki.domain.common.AbstractAnkiException;

public class DeckIdIsRequired extends AbstractAnkiException {
    protected DeckIdIsRequired() {
        super("Deck id is required in a deck");
    }
}
