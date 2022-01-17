package com.stemlaur.anki.domain.common.spi;

import com.stemlaur.anki.domain.common.DomainEvent;

import java.util.List;

public interface DomainEvents {

    void publish(DomainEvent event);

    default void publish(List<DomainEvent> events) {
        events.forEach(this::publish);
    }
}
