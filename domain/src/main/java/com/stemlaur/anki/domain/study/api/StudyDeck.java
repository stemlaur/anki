package com.stemlaur.anki.domain.study.api;

import com.stemlaur.anki.domain.study.CardToStudy;
import com.stemlaur.anki.domain.study.Opinion;

import java.util.Optional;

public interface StudyDeck {

    String startStudySession(final String deckId);

    Optional<CardToStudy> nextCardToStudy(final String sessionId);

    void study(final String sessionId, final String cardId, final Opinion opinion);
}
