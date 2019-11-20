package com.stemlaur.anki.domain;

public final class CardToStudy {
    private final String id;
    private final String question;

    public CardToStudy(final String id, final String question) {
        this.id = id;
        this.question = question;
    }

    public String id() {
        return id;
    }

    public String question() {
        return question;
    }
}
