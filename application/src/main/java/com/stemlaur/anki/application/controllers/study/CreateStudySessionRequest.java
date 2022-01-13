package com.stemlaur.anki.application.controllers.study;

import com.fasterxml.jackson.annotation.JsonProperty;

final class CreateStudySessionRequest {
    private final String deckId;

    public CreateStudySessionRequest(@JsonProperty("deckId") final String deckId) {
        this.deckId = deckId;
    }

    public String getDeckId() {
        return deckId;
    }
}
