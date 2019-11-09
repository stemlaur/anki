package com.stemlaur.anki.domain;

import com.stemlaur.anki.domain.common.AbstractEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public final class CardAdded extends AbstractEvent {
    private final String deckId;
    private final int cardId;
    private final String cardDetail;

    public CardAdded(final String deckId, final int cardId, final String cardDetail) {
        this.deckId = deckId;
        this.cardId = cardId;
        this.cardDetail = cardDetail;
    }
}
