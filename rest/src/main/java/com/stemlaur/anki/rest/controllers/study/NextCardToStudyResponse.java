package com.stemlaur.anki.rest.controllers.study;

import lombok.Value;

@Value
final class NextCardToStudyResponse {
    private final String id;
    private final String question;
    private final String answer;
}
