package com.stemlaur.anki.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@EqualsAndHashCode
public final class Session {
    private final String id;
    private final String studentId;
    private final Set<CardToStudy> cardsToStudy;

    public Session(final String id,
                   final String studentId,
                   final Set<CardToStudy> cardsToStudy) {
        this.id = id;
        this.studentId = studentId;
        this.cardsToStudy = cardsToStudy;
    }

    public String id() {
        return this.id;
    }

    public Set<CardToStudy> cardsToStudy() {
        return this.cardsToStudy;
    }
}
