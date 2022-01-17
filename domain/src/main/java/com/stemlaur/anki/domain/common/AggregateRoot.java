package com.stemlaur.anki.domain.common;

import java.util.List;

public interface AggregateRoot {

    List<DomainEvent> events();
}
