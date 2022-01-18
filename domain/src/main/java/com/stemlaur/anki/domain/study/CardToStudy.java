package com.stemlaur.anki.domain.study;

import com.stemlaur.livingdocumentation.annotation.Entity;

/**
 * Represents a card being studied.
 */
@Entity
public final class CardToStudy {
    private final String id;
    private final String question;
    private final String answer;

    public CardToStudy(final String id, final String question, final String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public String id() {
        return id;
    }

    public String question() {
        return question;
    }

    public String answer() {
        return this.answer;
    }
}
