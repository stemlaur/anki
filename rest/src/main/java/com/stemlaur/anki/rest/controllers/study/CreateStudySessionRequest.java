package com.stemlaur.anki.rest.controllers.study;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
final class CreateStudySessionRequest {
    private String deckId;
}
