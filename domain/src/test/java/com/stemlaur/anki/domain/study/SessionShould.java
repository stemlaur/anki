package com.stemlaur.anki.domain.study;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class SessionShould {
    private static final String SESSION_ID = "9bbd69b9-617d-4945-9180-91184ac722ab";
    private static final String ANOTHER_SESSION_ID = "d5b9a36f-3590-444e-949c-49e3faae0ed3";

    @Test
    public void beIdentifiedByItsId() {
        final Session firstSession = new Session(SESSION_ID, Collections.singleton(new CardToStudy("1", "question 1", "answer 1")));
        final Session secondSession = new Session(SESSION_ID, Collections.singleton(new CardToStudy("2", "question 2", "answer 2")));
        final Session differentSession = new Session(ANOTHER_SESSION_ID, Collections.singleton(new CardToStudy("1", "question 1", "answer 1")));

        assertEquals(firstSession, secondSession);
        assertNotEquals(firstSession, differentSession);
    }
}