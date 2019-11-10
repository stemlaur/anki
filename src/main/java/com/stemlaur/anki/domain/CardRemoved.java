package com.stemlaur.anki.domain;

import com.stemlaur.anki.domain.common.AbstractEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public final class CardRemoved extends AbstractEvent {

    private final String deckId;
    private final int cardId;

    public CardRemoved(final String deckId, final int cardId) {

        this.deckId = deckId;
        this.cardId = cardId;
    }
}
