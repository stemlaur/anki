package com.stemlaur.anki.domain;

import com.stemlaur.anki.domain.common.AbstractEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public final class DeckCreated extends AbstractEvent {
    private final String id;
    private final String title;

    public DeckCreated(final String id, final String title) {
        super();
        this.id = id;
        this.title = title;
    }
}
