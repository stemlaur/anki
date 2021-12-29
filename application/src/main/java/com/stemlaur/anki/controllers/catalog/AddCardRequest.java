package com.stemlaur.anki.controllers.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

final class AddCardRequest {
    private final String question;
    private final String answer;

    public AddCardRequest(@JsonProperty("question") final String question,
                          @JsonProperty("answer") final String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
