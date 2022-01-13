package com.stemlaur.anki.controllers.study;

import com.fasterxml.jackson.annotation.JsonProperty;

final class CreateStudySessionRequest {
    private String deckId;

    public CreateStudySessionRequest(@JsonProperty("deckId") final String deckId) {
        this.deckId = deckId;
    }

    public String getDeckId() {
        return deckId;
    }
}
