package com.stemlaur.anki.domain.common;

import lombok.Getter;

import java.util.List;

@Getter
public final class Tuple<EVENT extends AbstractEvent, AGGREGATE> {
    private final List<EVENT> events;
    private final Object second;

    public Tuple(final List<EVENT> events, final AGGREGATE aggregate) {
        this.events = events;
        this.second = aggregate;
    }
}
