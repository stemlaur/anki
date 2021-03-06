package com.stemlaur.anki.domain.catalog;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(of = {"id"})
public final class Deck {
    private final String id;
    private final String title;
    private List<Card> cards = new ArrayList<>();
    private int cardIdCounter = 1;

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

    public int addCard(final CardDetail cardDetail) {
        Validate.notNull(cardDetail);
        final int id = cardIdCounter;
        this.cards.add(new Card(id, cardDetail));
        cardIdCounter++;
        return id;
    }

    public void removeCard(final int id) {
        this.cards = this.cards.stream().filter(c -> c.id() != id).collect(Collectors.toList());
    }

    public String id() {
        return this.id;
    }

    public List<Card> cards() {
        return Collections.unmodifiableList(this.cards);
    }

    public String title() {
        return this.title;
    }

    public static class DeckIdIsRequired extends RuntimeException {
    }

    public static class DeckTitleIsRequired extends RuntimeException {
    }
}
