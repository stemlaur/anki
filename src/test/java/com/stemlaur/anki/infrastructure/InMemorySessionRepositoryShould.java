package com.stemlaur.anki.infrastructure;

import com.stemlaur.anki.domain.CardToStudy;
import com.stemlaur.anki.domain.Session;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class InMemorySessionRepositoryShould {
    private static final String STUDENT_ID = "54321";
    private static final String SESSION_ID = "901240234";
    private static final String TECH_QUESTION = "??";

    private InMemorySessionRepository inMemorySessionRepository;

    @Before
    public void setUp() throws Exception {
        this.inMemorySessionRepository = new InMemorySessionRepository();
    }

    @Test
    public void saveSession() {
        assertTrue(this.inMemorySessionRepository.findById(SESSION_ID).isEmpty());

        this.inMemorySessionRepository.save(new Session(
                SESSION_ID, STUDENT_ID,
                Collections.singleton(new CardToStudy("1234", TECH_QUESTION))));

        assertTrue(this.inMemorySessionRepository.findById(SESSION_ID).isPresent());
    }
}
