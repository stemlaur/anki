package com.stemlaur.anki.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void addCard(final Card card) {
        Validate.notNull(card);
        this.cards.add(card);
    }

    public String id() {
        return this.id;
    }

    public List<Card> cards() {
        return Collections.unmodifiableList(cards);
    }

    public static class DeckIdIsRequired extends RuntimeException {
    }

    public static class DeckTitleIsRequired extends RuntimeException {
    }
}
