package com.stemlaur.anki.domain.catalog.api;

import com.stemlaur.anki.domain.catalog.CardDetail;

public interface AddCard {
    void addCard(final String deckId, final CardDetail cardDetail);
}
