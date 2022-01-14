package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.common.AbstractAnkiException;

public class DeckDoesNotContainAnyCards extends AbstractAnkiException {
    public DeckDoesNotContainAnyCards(final String id) {
        super("Deck with id %s does not contain any card", id);
    }
}
