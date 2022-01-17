package com.stemlaur.anki.domain.catalog;

import com.stemlaur.anki.domain.common.DomainEvent;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * Event representing the fact that a deck is created.
 */
@com.stemlaur.livingdocumentation.annotation.DomainEvent
public class DeckCreated implements DomainEvent {
    private final String deckId;
    private final String title;

    public DeckCreated(final DeckId deckId,
                       final String title) {
        this.deckId = deckId.getValue();
        this.title = title;
    }

    @Override
    public String getAggregateId() {
        return this.deckId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DeckCreated that = (DeckCreated) o;
        return deckId.equals(that.deckId) && title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deckId, title);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("deckId", deckId)
                .append("title", title)
                .toString();
    }
}
