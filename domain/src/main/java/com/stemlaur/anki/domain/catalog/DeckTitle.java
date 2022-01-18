package com.stemlaur.anki.domain.catalog;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.stemlaur.livingdocumentation.annotation.ValueObject;

import static org.apache.commons.lang3.Validate.inclusiveBetween;
import static org.apache.commons.lang3.Validate.notBlank;

/**
 * This value object represents the title of a deck.
 */
@ValueObject
public class DeckTitle {
    private final String value;

    public DeckTitle(String value) {
        this.value = notBlank(value, "The title must not be blank");
        inclusiveBetween(3, 100, value.length(), "Deck title value length should be between 3 and 100");
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DeckTitle deckTitle = (DeckTitle) o;

        return new EqualsBuilder().append(value, deckTitle.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
