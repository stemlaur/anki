package com.stemlaur.anki.domain;

import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode
public final class Session {
    private final String id;
    private final Set<CardToStudy> cardsToStudy;

    public Session(final String id,
                   final Set<CardToStudy> cardsToStudy) {
        this.id = id;
        this.cardsToStudy = cardsToStudy;
    }

    public String id() {
        return this.id;
    }

    public Set<CardToStudy> cardsToStudy() {
        return this.cardsToStudy;
    }
}
