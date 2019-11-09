package com.stemlaur.anki.domain;

import org.apache.commons.lang3.StringUtils;

public final class Deck {
    private final String id;
    private final String title;

    public Deck(final String id, final String title) {
        this.id = id;
        this.title = title;
        if(id == null) {
            throw new DeckIdIsRequired();
        }
        if(StringUtils.isBlank(title)) {
            throw new DeckTitleIsRequired();
        }
    }

    public String title() {
        return this.title;
    }

    public String id() {
        return this.id;
    }

    public static class DeckIdIsRequired extends RuntimeException{}
    public static class DeckTitleIsRequired extends RuntimeException{}
}
