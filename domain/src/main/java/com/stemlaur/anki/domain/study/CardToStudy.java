package com.stemlaur.anki.domain.study;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

@Entity
public final class CardToStudy {
    @Identity
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
