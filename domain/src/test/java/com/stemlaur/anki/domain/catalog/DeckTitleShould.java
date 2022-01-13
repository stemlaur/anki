package com.stemlaur.anki.domain.catalog;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DeckTitleShould {

    @Test
    void beAValueObject() {
        DeckTitle firstDeckTitle = new DeckTitle("my first title");
        DeckTitle secondDeckTitle = new DeckTitle("my second title");

        assertEquals(firstDeckTitle, firstDeckTitle);
        assertNotEquals(firstDeckTitle, secondDeckTitle);

        assertEquals(firstDeckTitle.hashCode(), firstDeckTitle.hashCode());
        assertNotEquals(firstDeckTitle.hashCode(), secondDeckTitle.hashCode());

        assertEquals("my first title", firstDeckTitle.toString());
    }

    @Test
    void notAcceptBlankValues() {
        NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> new DeckTitle(null));
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new DeckTitle("   "));

        assertThat(nullPointerException.getMessage()).isEqualTo("The title must not be blank");
        assertThat(illegalArgumentException.getMessage()).isEqualTo("The title must not be blank");

    }

    @Test
    void notAcceptValuesLongerThan100Chars() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new DeckTitle(StringUtils.repeat("A", 101)));
        assertThat(exception.getMessage()).isEqualTo("Deck title value length should be between 3 and 100");
    }

    @Test
    void notAcceptValuesShorterThan3Chars() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new DeckTitle("ab"));
        assertThat(exception.getMessage()).isEqualTo("Deck title value length should be between 3 and 100");
    }
}