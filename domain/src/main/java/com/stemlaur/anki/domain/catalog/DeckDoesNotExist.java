package com.stemlaur.anki.domain.catalog;

import com.stemlaur.anki.domain.common.AbstractAnkiException;

public class DeckDoesNotExist extends AbstractAnkiException {
    public DeckDoesNotExist(final String id) {
        super("Deck with id %s does not exist", id);
    }
}
