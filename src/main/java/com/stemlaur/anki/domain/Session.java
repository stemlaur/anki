package com.stemlaur.anki.domain;

import lombok.EqualsAndHashCode;

import java.util.Optional;
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

    public Optional<CardToStudy> findCard(final String cardId) {
        return this.cardsToStudy.stream()
                .filter(cardToStudy -> cardToStudy.id().equals(cardId))
                .findFirst();
    }
}
