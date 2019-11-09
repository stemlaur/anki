package com.stemlaur.anki.domain.common;

import lombok.Getter;

@Getter
public final class Tuple<EVENT extends AbstractEvent, AGGREGATE> {
    private final EVENT event;
    private final AGGREGATE aggregate;

    public Tuple(final EVENT event, final AGGREGATE aggregate) {
        this.event = event;
        this.aggregate = aggregate;
    }
}
