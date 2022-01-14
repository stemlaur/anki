package com.stemlaur.anki.domain.catalog;

import com.stemlaur.anki.domain.common.AbstractAnkiException;

public class DeckTitleIsRequired extends AbstractAnkiException {
    protected DeckTitleIsRequired() {
        super("Deck title is required in a deck");
    }
}
