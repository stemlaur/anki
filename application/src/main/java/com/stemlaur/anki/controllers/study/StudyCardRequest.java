package com.stemlaur.anki.controllers.study;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stemlaur.anki.domain.study.Opinion;

final class StudyCardRequest {
    private final String cardId;
    private final Opinion opinion;

    public StudyCardRequest(@JsonProperty("cardId") final String cardId,
                            @JsonProperty("opinion") final Opinion opinion) {
        this.cardId = cardId;
        this.opinion = opinion;
    }

    public String getCardId() {
        return cardId;
    }

    public Opinion getOpinion() {
        return opinion;
    }
}
