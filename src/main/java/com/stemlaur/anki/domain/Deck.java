package com.stemlaur.anki.domain;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Deck {
    private final String id;
    private final String title;
    private List<Card> cards = new ArrayList<>();

    public Deck(final String id, final String title) {
        this.id = id;
        this.title = title;
        if (StringUtils.isBlank(id)) {
            throw new DeckIdIsRequired();
        }
        if (StringUtils.isBlank(title)) {
            throw new DeckTitleIsRequired();
        }
    }

    public String title() {
        return this.title;
    }

    public void addCard(final CardDetail cardDetail) {
        Validate.notNull(cardDetail);
        this.cards.add(new Card(0, cardDetail));
    }

    public String id() {
        return this.id;
    }

    public List<CardDetail> cards() {
        return cards.stream().map(c -> c.detail).collect(Collectors.toUnmodifiableList());
    }

    @EqualsAndHashCode
    public static class Card {
        private final int id;
        private final CardDetail detail;

        public Card(final int id, final CardDetail detail) {
            this.id = id;
            this.detail = detail;
        }
    }

    public static class DeckIdIsRequired extends RuntimeException {
    }

    public static class DeckTitleIsRequired extends RuntimeException {
    }
}
