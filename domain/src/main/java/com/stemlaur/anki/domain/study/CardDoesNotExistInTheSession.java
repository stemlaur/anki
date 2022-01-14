package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.AbstractAnkiException;

public class CardDoesNotExistInTheSession extends AbstractAnkiException {
    public CardDoesNotExistInTheSession(final String sessionId, final String cardId) {
        super("Card with id %s does not exist in the session %s", cardId, sessionId);
    }
}
