package com.stemlaur.anki.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeckStudyServiceNextCardToStudyShould {

    private static final String SESSION_ID = "901240234";
    private static final String TECH_QUESTION = "??";

    private DeckStudyService deckStudyService;
    @Mock
    private DeckService deckService;
    @Mock
    private SessionRepository sessionRepository;

    @Before
    public void setUp() {
        this.deckStudyService = new DeckStudyService(
                this.deckService, null, this.sessionRepository);
    }

    @Test(expected = DeckStudyService.SessionDoesNotExist.class)
    public void throwAnException_when_sessionDoesNotExist() {
        when(this.sessionRepository.findById(SESSION_ID)).thenReturn(empty());
        this.deckStudyService.nextCardToStudy(SESSION_ID);
    }

    @Test
    public void returnACardToStudy_when_sessionContainsOne() {
        final Session session = new Session(
                SESSION_ID,
                Collections.singleton(new CardToStudy(any(), TECH_QUESTION)));
        when(this.sessionRepository.findById(SESSION_ID)).thenReturn(of(session));

        final Optional<CardToStudy> cardToStudy =
                this.deckStudyService.nextCardToStudy(SESSION_ID);
        assertFalse(cardToStudy.isEmpty());
    }
}