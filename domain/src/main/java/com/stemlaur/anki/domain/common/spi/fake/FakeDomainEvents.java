package com.stemlaur.anki.domain.common.spi.fake;

import com.stemlaur.anki.domain.common.DomainEvent;
import com.stemlaur.anki.domain.common.spi.DomainEvents;

import java.util.ArrayList;
import java.util.List;

public class FakeDomainEvents implements DomainEvents {
    private final List<DomainEvent> events = new ArrayList<>();

    @Override
    public void publish(final DomainEvent event) {
        events.add(event);
    }

    public List<DomainEvent> getEvents() {
        return events;
    }

    public void clear() {
        events.clear();
    }
}
