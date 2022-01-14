package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.common.AbstractAnkiException;

public class SessionDoesNotExist extends AbstractAnkiException {
    public SessionDoesNotExist(final String id) {
        super("Session with id %s does not exist", id);
    }
}
