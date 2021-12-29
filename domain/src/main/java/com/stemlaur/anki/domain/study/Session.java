package com.stemlaur.anki.domain.study;
import java.util.Optional;
import java.util.Set;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Session session = (Session) o;

        return id.equals(session.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
