package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.AbstractAnkiException;//@formatter:off
public class DeckDoesNotExist extends AbstractAnkiException {
    public DeckDoesNotExist(final String id) {
        super("Deck with id %s does not exist", id);
    }
}
