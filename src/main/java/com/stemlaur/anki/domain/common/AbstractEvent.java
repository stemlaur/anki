package com.stemlaur.anki.domain.common;

import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode
public abstract class AbstractEvent {
    private final Instant when;

    protected AbstractEvent(final Instant when) {
        this.when = when;
    }
}
