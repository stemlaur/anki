package com.stemlaur.anki.domain.feature;

import com.stemlaur.anki.domain.Opinion;

public class DeckStudyService {
    public String startStudySession(final String studentId, final String deckId) {
        return null;
    }

    public CardToStudy nextCardToStudy(final String studentId, final String sessionId) {
        return new CardToStudy();
    }

    public void study(final String studentId, final String sessionId, final String cardId, final Opinion opinion) {

    }
}
