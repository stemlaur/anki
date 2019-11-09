package com.stemlaur.anki.domain.common;

import lombok.Getter;

import java.util.List;

@Getter
public final class TupleWithMultipleEvents<AGGREGATE> {
    private final List<? extends AbstractEvent> events;
    private final AGGREGATE aggregate;

    public TupleWithMultipleEvents(final List<? extends AbstractEvent> events, final AGGREGATE aggregate) {
        this.events = events;
        this.aggregate = aggregate;
    }
}
