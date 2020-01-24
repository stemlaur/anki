package com.stemlaur.anki.infrastructure;

import com.stemlaur.anki.domain.study.CardToStudy;
import com.stemlaur.anki.domain.study.Session;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class InMemorySessionsShould {
    private static final String SESSION_ID = "0ae41528-e41d-4737-af4a-3858afd71036";
    private static final String CARD_ID = "25cfd613-a150-4cb9-bc21-6a5cc8871c87";
    private static final String QUESTION = "What is the ocipital lobe involved in ?";
    private static final String ANSWER = "Visual perception.";

    private InMemorySessions inMemorySessions;

    @Before
    public void setUp() {
        this.inMemorySessions = new InMemorySessions();
    }

    @Test
    public void saveSession() {
        assertTrue(this.inMemorySessions.find(SESSION_ID).isEmpty());

        this.inMemorySessions.save(new Session(
                SESSION_ID,
                Collections.singleton(new CardToStudy(CARD_ID, QUESTION, ANSWER))));

        assertTrue(this.inMemorySessions.find(SESSION_ID).isPresent());
    }
}
